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

package org.tquadrat.foundation.perflog.remote;

import static org.apiguardian.api.API.Status.STABLE;
import static org.tquadrat.foundation.mgmt.JMXUtils.composeServiceURL;

import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXServiceURL;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apiguardian.api.API;
import org.tquadrat.foundation.annotation.ClassVersion;
import org.tquadrat.foundation.perflog.remote.internal.PerfLogRemoteImpl;

/**
 *  <p>{@summary The declaration of a remote client for the Foundation
 *  Performance Logging and Monitoring.}</p>
 *
 *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
 *  @version $Id: PerfLogRemote.java 1229 2026-05-04 19:11:41Z tquadrat $
 *  @since 0.25.0
 *
 *  @UMLGraph.link
 */
@ClassVersion( sourceVersion = "$Id: PerfLogRemote.java 1229 2026-05-04 19:11:41Z tquadrat $" )
@API( status = STABLE, since = "0.25.0" )
public sealed interface PerfLogRemote extends AutoCloseable
    permits PerfLogRemoteImpl
{
        /*-----------*\
    ====** Constants **========================================================
        \*-----------*/
    /**
     *  <p>{@summary The domain name part of the
     *  {@link ObjectName}
     *  identifying the Performance Logging MBean
     *  in the MBean server: {@value}}.</p>
     */
    public static final String DOMAIN_NAME = "org.tquadrat.foundation.PerfLog";

    /**
     *  The name of the JSON boolean that holds the aborted flag:
     *  {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Aborted = "Aborted";

    /**
     *  The name of the JSON Number that holds the number of aborted runs for
     *  the performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_AbortedRuns = "Aborted";

    /**
     *  The name of the JSON String that holds the cause for the abort of a
     *  performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Cause = "Cause";

    /**
     *  The name of the JSON Number that holds the number of completed runs for
     *  the performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_CompletedRuns = "Completed";

    /**
     *  The name of the JSON Object that holds the performance section context:
     *  {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Context = "Context";

    /**
     *  The name of the JSON Object that holds the elapsed time: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_ElapsedTime = "ElapsedTime";

    /**
     *  The name of the JSON Object that holds an error message: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Error = "Error";

    /**
     *  The name of the JSON boolean that holds threshold exceeded flag:
     *  {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_ExceededThreshold = "ExceededThreshold";

    /**
     *  The name of the JSON String that holds the time of the first start of
     *  the performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_FirstStart = "FirstStart";

    /**
     *  The name of the JSON String that holds the time when the performance
     *  section was last updated: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_LastUpdated = "LastUpdated";

    /**
     *  The name of the JSON String that holds the text of an error message:
     *  {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Message = "Message";

    /**
     *  The name of the JSON Object that holds the data from the performance
     *  section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Section = "PerformanceSection";

    /**
     *  The name of the JSON String that holds the description of the
     *  performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionDescription = "Description";

    /**
     *  The name of the JSON Boolean that holds the flag indicating whether the
     *  performance section is currently ignored: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionIgnored = "Ignored";

    /**
     *  The name of the JSON String that holds the name of the performance
     *  section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionName = "Name";

    /**
     *  The name of the JSON Object that holds the execution statistics of the
     *  performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionStatistics = "Statistics";

    /**
     *  The name of the JSON Object that holds the threshold time from the
     *  performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionThreshold = "Threshold";

    /**
     *  The name of the JSON boolean that holds the flag indicating whether a
     *  report should be sent only when the threshold was exceeded: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionThresholdOnlyReport = "ThresholdOnlyReport";

    /**
     *  The name of the JSON Object that holds the timeout time from the
     *  performance section: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_SectionTimeout = "Timeout";

    /**
     *  The name of the JSON String that holds the time when the performance
     *  section was entered: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_StartTime = "StartTime";

    /**
     *  The name of the JSON Object that holds a success message: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Success = "Success";

    /**
     *  The name of the JSON Number that holds the number of performance
     *  section runs where the threshold was exceeded: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_ThresholdExceededRuns = "ThresholdExceeded";

    /**
     *  The name of the JSON boolean that holds timed out flag: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_TimedOut = "TimedOut";

    /**
     *  The name of the JSON Number that holds the number of performance
     *  section runs that timed out: {@value}.
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_TimedOutRuns = "TimedOut";

    /**
     *  The name of the JSON String that holds the unit for the dimension from
     *  a dimensioned value: {@value}.
     *
     *  @see #JSONField_SectionThreshold
     *  @see #JSONField_SectionTimeout
     */
    @SuppressWarnings( "StaticMethodOnlyUsedInOneClass" )
    public static final String JSONField_Unit = "Unit";

    /**
     *  The name of the JSON Number that holds the value from a dimensioned
     *  value: {@value}.
     *
     *  @see #JSONField_SectionThreshold
     *  @see #JSONField_SectionTimeout
     */
    public static final String JSONField_Value = "Value";

    /**
     *  The type for the
     *  {@link ObjectName}
     *  identifying the Performance Logging MBean
     *  in the MBean server: {@value}.
     */
    public static final String MBEAN_TYPE = "PerformanceLogging";

    /**
     *  The type for the
     *  {@link javax.management.Notification}
     *  instances emitted by Performance Logging MBean: {@value}.
     */
    public static final String NOTIFICATION_Type = "org.tquadrat.foundation.perflog.SectionExec";

        /*---------*\
    ====** Methods **==========================================================
        \*---------*/
    /**
     *  {@inheritDoc}
     */
    @Override
    public void close();

    /**
     *  <p>{@summary Connects to the Performance Logging MBean specified
     *  through the given port number and the
     *  {@link ObjectName}
     *  returned by
     *  {@link #getPerfLogMBeanObjectName()}.}</p>
     *  <p>This method creates a local connection (both processes are running
     *  on the same machine).</p>
     *
     *  @param  registryPort    The number of the registry port.
     *  @param  listener    The notification listener.
     *  @return The {@code PerfLogRemote} instance that manages the connection
     *      to the performance logging MBean.
     *  @throws IOException Unable to connect to the MBean server.
     *  @throws InstanceNotFoundException   There is no performance logging
     *      MBean registered on the MBean server.
     */
    @SuppressWarnings( "ClassReferencesSubclass" )
    public static PerfLogRemote connect( final int registryPort, final NotificationListener listener ) throws InstanceNotFoundException, IOException
    {
        final var url = composeServiceURL( registryPort );
        final var retValue = PerfLogRemoteImpl.connect( url, listener );

        //---* Done *----------------------------------------------------------
        return retValue;
    }   //  connect()

    /**
     *  <p>{@summary Connects to the Performance Logging MBean specified
     *  through the given host name, port numbers and the
     *  {@link ObjectName}
     *  returned by
     *  {@link #getPerfLogMBeanObjectName()}.}</p>
     *  <p>This method creates a remote connection (both processes are probably
     *  running on different machines).</p>
     *
     *  @param  hostName    The host name.
     *  @param  registryPort    The number of the registry port.
     *  @param  dataPort    The number of the data port; can be the same as the
     *      registry port.
     *  @param  listener    The notification listener.
     *  @return The {@code PerfLogRemote} instance that manages the connection
     *      to the performance logging MBean.
     *  @throws MalformedURLException   It is not possible to compose a valid
     *      {@link JMXServiceURL}
     *      with the given {@code hostName}.
     *  @throws IOException Unable to connect to the MBean server.
     *  @throws InstanceNotFoundException   There is no performance logging
     *      MBean registered on the MBean server.
     */
    @SuppressWarnings( "ClassReferencesSubclass" )
    public static PerfLogRemote connect( final String hostName, final int registryPort, final int dataPort, final NotificationListener listener ) throws IOException, InstanceNotFoundException
    {
        final var url = composeServiceURL( hostName, registryPort, dataPort );
        final var retValue = PerfLogRemoteImpl.connect( url, listener );

        //---* Done *----------------------------------------------------------
        return retValue;
    }   //  connect()

    /**
     *  Returns the
     *  {@link MBeanInfo}
     *  for the performance logging MBean.
     *
     *  @return The {@code MBeanInfo}.
     *  @throws IllegalStateException   The instance was already closed.
     *  @throws IntrospectionException  An exception occurred during
     *      introspection.
     *  @throws InstanceNotFoundException   The MBean specified was not found.
     *  @throws ReflectionException An exception occurred when trying to invoke
     *      the method
     *      {@link DynamicMBean#getMBeanInfo() getMBeanInfo()} of a
     *      {@linkplain DynamicMBean Dynamic MBean}.
     *  @throws IOException A communication problem occurred when talking to
     *      the MBean server.
     */
    public MBeanInfo getMBeanInfo() throws IllegalStateException, ReflectionException, InstanceNotFoundException, IntrospectionException, IOException;

    /**
     *  Returns the
     *  {@link ObjectName}
     *  for the Performance Logging MBean.
     *
     *  @return The object name for the MBean.
     */
    public static ObjectName getPerfLogMBeanObjectName() { return PerfLogRemoteImpl.getPerfLogMBeanObjectName(); }

    /**
     *  <p>{@summary Returns the status for the given performance section.}</p>
     *
     *  @param  name    The name of the performance section.
     *  @return The status of the performance section, or a message indicating
     *      what failed in case of error, as a JSON String.
     *  @throws IllegalStateException   The instance was already closed.
     *  @throws ReflectionException An exception occurred when trying to invoke
     *      the method
     *      {@link DynamicMBean#getAttribute(String) getAttribute()} of a
     *      {@linkplain DynamicMBean Dynamic MBean}.
     *  @throws MBeanException  Wraps an exception thrown by the MBean method.
     *  @throws InstanceNotFoundException   The MBean specified was not found.
     *  @throws IOException A communication problem occurred when talking to
     *      the MBean server.
     */
    public String getPerformanceSection( final String name ) throws IllegalStateException, ReflectionException, InstanceNotFoundException, MBeanException, IOException;

    /**
     *  <p>{@summary Returns a list of the currently defined performance
     *  sections.}</p>
     *
     *  @return The list of the performance section names.
     *  @throws IllegalStateException   The instance was already closed.
     *  @throws ReflectionException An exception occurred when trying to invoke
     *      the method
     *      {@link DynamicMBean#getAttribute(String) getAttribute()} of a
     *      {@linkplain DynamicMBean Dynamic MBean}.
     *  @throws MBeanException  Wraps an exception thrown by the MBean method.
     *  @throws InstanceNotFoundException   The MBean specified was not found.
     *  @throws AttributeNotFoundException  The attribute was missing.
     *  @throws IOException A communication problem occurred when talking to
     *      the MBean server.
     */
    @SuppressWarnings( "MethodWithTooExceptionsDeclared" )
    public String [] getPerformanceSections() throws IllegalStateException, ReflectionException, AttributeNotFoundException, InstanceNotFoundException, MBeanException, IOException;
}
//  interface PerfLogRemote

/*
 *  End of File
 */