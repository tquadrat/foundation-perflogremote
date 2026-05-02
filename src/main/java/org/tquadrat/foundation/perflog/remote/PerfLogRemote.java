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

import javax.management.ObjectName;

import org.apiguardian.api.API;
import org.tquadrat.foundation.annotation.ClassVersion;
import org.tquadrat.foundation.perflog.remote.internal.PerfLogRemoteImpl;

/**
 *  <p>{@summary The declaration of a remote client for the Foundation
 *  Performance Logging and Monitoring.}</p>
 *
 *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
 *  @version $Id: PerfLogRemote.java 1216 2026-05-02 11:16:24Z tquadrat $
 *  @since 0.25.0
 *
 *  @UMLGraph.link
 */
@ClassVersion( sourceVersion = "$Id: PerfLogRemote.java 1216 2026-05-02 11:16:24Z tquadrat $" )
@API( status = STABLE, since = "0.25.0" )
public sealed interface PerfLogRemote
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
    public static final String JSONField_Aborted = "Aborted";

    /**
     *  The name of the JSON Number that holds the number of aborted runs for
     *  the performance section: {@value}.
     */
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
    public static final String JSONField_CompletedRuns = "Completed";

    /**
     *  The name of the JSON Object that holds the performance section context:
     *  {@value}.
     */
    public static final String JSONField_Context = "Context";

    /**
     *  The name of the JSON Object that holds the elapsed time: {@value}.
     */
    public static final String JSONField_ElapsedTime = "ElapsedTime";

    /**
     *  The name of the JSON Object that holds an error message: {@value}.
     */
    public static final String JSONField_Error = "Error";

    /**
     *  The name of the JSON boolean that holds threshold exceeded flag:
     *  {@value}.
     */
    public static final String JSONField_ExceededThreshold = "ExceededThreshold";

    /**
     *  The name of the JSON String that holds the time of the first start of
     *  the performance section: {@value}.
     */
    public static final String JSONField_FirstStart = "FirstStart";

    /**
     *  The name of the JSON String that holds the time when the performance
     *  section was last updated: {@value}.
     */
    public static final String JSONField_LastUpdated = "LastUpdated";

    /**
     *  The name of the JSON String that holds the text of an error message:
     *  {@value}.
     */
    public static final String JSONField_Message = "Message";

    /**
     *  The name of the JSON Object that holds the data from the performance
     *  section: {@value}.
     */
    public static final String JSONField_Section = "PerformanceSection";

    /**
     *  The name of the JSON String that holds the description of the
     *  performance section: {@value}.
     */
    public static final String JSONField_SectionDescription = "Description";

    /**
     *  The name of the JSON Boolean that holds the flag indicating whether the
     *  performance section is currently ignored: {@value}.
     */
    public static final String JSONField_SectionIgnored = "Ignored";

    /**
     *  The name of the JSON String that holds the name of the performance
     *  section: {@value}.
     */
    public static final String JSONField_SectionName = "Name";

    /**
     *  The name of the JSON Object that holds the execution statistics of the
     *  performance section: {@value}.
     */
    public static final String JSONField_SectionStatistics = "Statistics";

    /**
     *  The name of the JSON Object that holds the threshold time from the
     *  performance section: {@value}.
     */
    public static final String JSONField_SectionThreshold = "Threshold";

    /**
     *  The name of the JSON boolean that holds the flag indicating whether a
     *  report should be sent only when the threshold was exceeded: {@value}.
     */
    public static final String JSONField_SectionThresholdOnlyReport = "ThresholdOnlyReport";

    /**
     *  The name of the JSON Object that holds the timeout time from the
     *  performance section: {@value}.
     */
    public static final String JSONField_SectionTimeout = "Timeout";

    /**
     *  The name of the JSON String that holds the time when the performance
     *  section was entered: {@value}.
     */
    public static final String JSONField_StartTime = "StartTime";

    /**
     *  The name of the JSON Object that holds a success message: {@value}.
     */
    public static final String JSONField_Success = "Success";

    /**
     *  The name of the JSON Number that holds the number of performance
     *  section runs where the threshold was exceeded: {@value}.
     */
    public static final String JSONField_ThresholdExceededRuns = "ThresholdExceeded";

    /**
     *  The name of the JSON boolean that holds timed out flag: {@value}.
     */
    public static final String JSONField_TimedOut = "TimedOut";

    /**
     *  The name of the JSON Number that holds the number of performance
     *  section runs that timed out: {@value}.
     */
    public static final String JSONField_TimedOutRuns = "TimedOut";

    /**
     *  The name of the JSON String that holds the unit for the dimension from
     *  a dimensioned value: {@value}.
     *
     *  @see #JSONField_SectionThreshold
     *  @see #JSONField_SectionTimeout
     */
    public static final String JSONField_Unit = "Unit";

    /**
     *  The name of the JSON Number that holds the value from a
     *  a dimensioned value: {@value}.
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
     *  instances emitted by this MBean: {@value}.
     */
    public static final String NOTIFICATION_Type = "org.tquadrat.foundation.perflog.SectionExec";

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
    public static ObjectName getPerfLogMBeanObjectName() { return PerfLogRemoteImpl.getPerfLogMBeanObjectName(); }

}
//  interface PerfLogRemote

/*
 *  End of File
 */