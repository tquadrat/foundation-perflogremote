/*
 * ============================================================================
 * Copyright © 2002-2026 by Thomas Thrien.
 * All Rights Reserved.
 * ============================================================================
 * Licensed to the public under the agreements of the GNU Lesser General Public
 * License, version 3.0 (the "License"). You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.tquadrat.foundation.perflog.remote.internal;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.tquadrat.foundation.lang.Objects.requireNonNullArgument;
import static org.tquadrat.foundation.lang.Objects.requireNotBlankArgument;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.ref.Cleaner;
import java.lang.ref.Cleaner.Cleanable;
import java.util.Hashtable;

import org.apiguardian.api.API;
import org.tquadrat.foundation.annotation.ClassVersion;
import org.tquadrat.foundation.perflog.remote.PerfLogRemote;

/**
 *  <p>{@summary The implementation of
 *  {@link PerfLogRemote }.}</p>
 *
 *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
 *  @version $Id: PerfLogRemoteImpl.java 1229 2026-05-04 19:11:41Z tquadrat $
 *  @since 0.25.0
 *
 *  @UMLGraph.link
 */
@ClassVersion( sourceVersion = "$Id: PerfLogRemoteImpl.java 1229 2026-05-04 19:11:41Z tquadrat $" )
@API( status = INTERNAL, since = "0.25.0" )
public final class PerfLogRemoteImpl implements PerfLogRemote
{
        /*---------------*\
    ====** Inner Classes **====================================================
        \*---------------*/
    /**
     *  <p>{@summary The janitor that takes care of the housekeeping for an
     *  instance of
     *  {@link PerfLogRemote}
     *  in case that was not properly closed.}</p>
     *
     *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
     *  @version $Id: PerfLogRemoteImpl.java 1229 2026-05-04 19:11:41Z tquadrat $
     *  @since 0.25.0
     *
     *  @param  objectName  The object name that is used to connect to the
     *      Performance Logging MBean.
     *  @param  connector   The JMX connector.
     *  @param  connection  The connection to the performance logging MBean.
     *  @param  url The service URL for the connection to the MBean server.
     *  @param  listener    The notification listener that receives the
     *      performance messages.
     *
     *  @UMLGraph.link
     */
    @ClassVersion( sourceVersion = "$Id: PerfLogRemoteImpl.java 1229 2026-05-04 19:11:41Z tquadrat $" )
    @API( status = INTERNAL, since = "0.25.0" )
    private record Janitor( ObjectName objectName, JMXServiceURL url, JMXConnector connector, MBeanServerConnection connection, NotificationListener listener ) implements Runnable
    {
            /*---------*\
        ====** Methods **======================================================
            \*---------*/
        /**
         *  {@inheritDoc}
         */
        @Override
        public final void run()
        {
            try
            {
                connection.removeNotificationListener( objectName, listener );
                connector.close();
            }
            catch( final InstanceNotFoundException | ListenerNotFoundException | IOException _ )
            {
                /*
                 * Deliberately ignored!
                 * The instance will be garbage collected anyway.
                 */
            }
        }   //  run()
    }
    //  record Janitor

        /*-----------*\
    ====** Constants **========================================================
        \*-----------*/

        /*------------*\
    ====** Attributes **=======================================================
        \*------------*/
    /**
     *  The
     *  {@link Cleanable}
     *  for this instance.
     */
    private final Cleanable m_Cleanable;

    /**
     *  The connection to the MBean.
     */
    private final MBeanServerConnection m_Connection;

    /**
     *  The flag the indicates whether this remote is (still) active.
     */
    private boolean m_IsActive;

    /**
     *  The caretaker for this instance.
     */
    @SuppressWarnings( "FieldCanBeLocal" )
    private final Janitor m_Janitor;

        /*------------------------*\
    ====** Static Initialisations **===========================================
        \*------------------------*/
    /**
     *  The cleaner that is used to finalise instances of
     *  {@code PerfLogRemoteImpl}.
     */
    private static final Cleaner m_Cleaner = Cleaner.create();

    /**
     *  The object name for the Performance Logging MBean.
     */
    private static final ObjectName m_ObjectName;

    static
    {
        try
        {
            final var attributes = new Hashtable<String,String>();
            attributes.put( "type", MBEAN_TYPE );
            m_ObjectName = ObjectName.getInstance( DOMAIN_NAME, attributes );
        }
        catch( final MalformedObjectNameException e )
        {
            throw new ExceptionInInitializerError( e );
        }
    }

        /*--------------*\
    ====** Constructors **=====================================================
        \*--------------*/
    /**
     *  Creates a new instance of {@code PerfLogRemoteImpl}.
     *
     *  @param  url The service URL for the connection to the MBean server.
     *  @param  listener    The notification listener that receives the
     *      performance messages.
     *  @throws IOException Unable to connect to the MBean server.
     *  @throws InstanceNotFoundException   There is no performance logging
     *      MBean registered on the MBean server.
     */
    public PerfLogRemoteImpl( final JMXServiceURL url, final NotificationListener listener ) throws InstanceNotFoundException, IOException
    {
        requireNonNullArgument( listener, "listener" );

        final var connector = JMXConnectorFactory.connect( requireNonNullArgument( url, "url" ) );
        m_Connection = connector.getMBeanServerConnection();

        final var objectName = getPerfLogMBeanObjectName();
        m_Connection.addNotificationListener( objectName, listener, null, null );

        //---* Register the housekeeping *-------------------------------------
        m_Janitor = new Janitor( objectName, url, connector, m_Connection, listener );
        //noinspection ThisEscapedInObjectConstruction
        m_Cleanable = m_Cleaner.register( this, m_Janitor );

        m_IsActive = true;
    }   //  PerfLogRemoteImpl()

        /*---------*\
    ====** Methods **==========================================================
        \*---------*/
    /**
     *  <p>{@summary Checks whether this performance logging remote is still
     *  active.} Throws an
     *  {@link IllegalStateException}
     *  if not.
     *
     *  @return {@code true} if the instance is still active.
     *  @throws IllegalStateException
     *      {@link #close()}
     *      was already called on this instance.
     */
    @SuppressWarnings( "UnusedReturnValue" )
    private final boolean checkActive() throws IllegalStateException
    {
        if( !m_IsActive ) throw new IllegalStateException( "PerfLogRemote was already terminated" );

        //---* Done *----------------------------------------------------------
        //noinspection ConstantValue
        return m_IsActive;
    }   //  checkActive()

    /**
     *  {@inheritDoc}
     */
    @Override
    public final void close()
    {
        if( m_IsActive )
        {
            m_Cleanable.clean();
            m_IsActive = false;
        }
    }   //  close()

    /**
     *  <p>{@summary Connects to the Performance Logging MBean specified
     *  through the given
     *  {@link JMXServiceURL}
     *  and the
     *  {@link ObjectName}
     *  returned by
     *  {@link #getPerfLogMBeanObjectName()}.}</p>
     *
     *  @param  url The JMX service URL.
     *  @param  listener    The notification listener.
     *  @return A new instance of {@code PerFlogRemote}.
     *  @throws IOException Unable to connect to the MBean server.
     *  @throws InstanceNotFoundException   There is no performance logging
     *      MBean registered on the MBean server.
     */
    public static final PerfLogRemoteImpl connect( final JMXServiceURL url, final NotificationListener listener ) throws InstanceNotFoundException, IOException
    {
        final var retValue = new PerfLogRemoteImpl( url, listener );

        //---* Done *----------------------------------------------------------
        return retValue;
    }   //  connect()

    /**
     *  {@inheritDoc}
     */
    @Override
    public final MBeanInfo getMBeanInfo() throws IllegalStateException, ReflectionException, InstanceNotFoundException, IntrospectionException, IOException
    {
        checkActive();

        final var retValue = m_Connection.getMBeanInfo( m_ObjectName );

        //---* Done *----------------------------------------------------------
        return retValue;
    }   //  getMBeanInfo()

    /**
     *  Returns the
     *  {@link ObjectName}
     *  for the Performance Logging MBean.
     *
     *  @return The object name for the MBean.
     */
    public static final ObjectName getPerfLogMBeanObjectName() { return m_ObjectName; }

    /**
     *  {@inheritDoc}
     */
    @Override
    public String getPerformanceSection( final String name ) throws IllegalStateException, ReflectionException, InstanceNotFoundException, MBeanException, IOException
    {
        checkActive();

        final var retValue = (String) m_Connection.invoke( m_ObjectName, "showPerformanceSection", new Object[] {requireNotBlankArgument( name, "name" )}, new String[] { String.class.getName() } );

        //---* Done *----------------------------------------------------------
        return retValue;
    }   //  getPerformanceSection()

    /**
     *  {@inheritDoc}
     */
    @SuppressWarnings( "MethodWithTooExceptionsDeclared" )
    @Override
    public final String[] getPerformanceSections() throws IllegalStateException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, IOException
    {
        checkActive();

        final var retValue = (String[]) m_Connection.getAttribute( m_ObjectName, "PerformanceSections" );

        //---* Done *----------------------------------------------------------
        return retValue;
    }
}
//  class PerfLogRemoteImpl

/*
 *  End of File
 */