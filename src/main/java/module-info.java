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

/**
 *  <p>{@summary The module for a remote client for the Foundation Performance
 *  Logging and Monitoring.}</p>
 */

module org.tquadrat.foundation.perflog.remote
{
    requires org.apiguardian.api;
    requires org.tquadrat.foundation.util;
    requires org.tquadrat.foundation.mgmt;
    requires java.management;

    //---* For common use *----------------------------------------------------
    exports org.tquadrat.foundation.perflog.remote;
}

/*
 *  End of File
 */