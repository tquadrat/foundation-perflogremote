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

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.Hashtable;

import org.apiguardian.api.API;
import org.tquadrat.foundation.annotation.ClassVersion;
import org.tquadrat.foundation.perflog.remote.PerfLogRemote;

/**
 *  <p>{@summary The implementation of
 *  {@link PerfLogRemote }.}</p>
 *
 *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
 *  @version $Id: PerfLogRemoteImpl.java 1216 2026-05-02 11:16:24Z tquadrat $
 *  @since 0.25.0
 *
 *  @UMLGraph.link
 */
@ClassVersion( sourceVersion = "$Id: PerfLogRemoteImpl.java 1216 2026-05-02 11:16:24Z tquadrat $" )
@API( status = INTERNAL, since = "0.25.0" )
public final class PerfLogRemoteImpl implements PerfLogRemote
{
        /*---------------*\
    ====** Inner Classes **====================================================
        \*---------------*/

        /*-----------*\
    ====** Constants **========================================================
        \*-----------*/

        /*------------*\
    ====** Attributes **=======================================================
        \*------------*/

        /*------------------------*\
    ====** Static Initialisations **===========================================
        \*------------------------*/
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

        /*---------*\
    ====** Methods **==========================================================
        \*---------*/
    /**
     *  Returns the
     *  {@link ObjectName}
     *  for the Performance Logging MBean.
     *
     *  @return The object name for the MBean.
     */
    public static final ObjectName getPerfLogMBeanObjectName() { return m_ObjectName; }


}
//  class PerfLogRemoteImpl

/*
 *  End of File
 */