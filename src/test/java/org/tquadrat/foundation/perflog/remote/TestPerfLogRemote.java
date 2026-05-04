/*
 * ============================================================================
 *  Copyright © 2002-2026 by Thomas Thrien.
 *  All Rights Reserved.
 * ============================================================================
 *  Licensed to the public under the agreements of the GNU Lesser General Public
 *  License, version 3.0 (the "License"). You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/lgpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */

package org.tquadrat.foundation.perflog.remote;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.tquadrat.foundation.perflog.remote.PerfLogRemote.connect;

import javax.management.NotificationListener;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.tquadrat.foundation.annotation.ClassVersion;
import org.tquadrat.foundation.exception.NullArgumentException;
import org.tquadrat.foundation.testutil.TestBaseClass;

/**
 *  <p>{@summary Some tests for
 *  {@link org.tquadrat.foundation.perflog.remote.internal.PerfLogRemoteImpl}.}</p>
 *
 *  @extauthor Thomas Thrien - thomas.thrien@tquadrat.org
 *  @version $Id: TestPerfLogRemote.java 1229 2026-05-04 19:11:41Z tquadrat $
 *  @since 0.25.0
 *
 */
@ClassVersion( sourceVersion = "$Id: TestPerfLogRemote.java 1229 2026-05-04 19:11:41Z tquadrat $" )
public class TestPerfLogRemote extends TestBaseClass
{
        /*---------*\
    ====** Methods **==========================================================
        \*---------*/
    /**
     *  Some test for
     *  {@link PerfLogRemote}
     *  and
     *  {@link org.tquadrat.foundation.perflog.remote.internal.PerfLogRemoteImpl}.
     *
     *  @throws Exception   Something went awfully wrong.
     */
    @Test
    final void testConnect() throws Exception
    {
        skipThreadTest();

        final NotificationListener listener = (_,_) -> {};

        assertThrows( NullArgumentException.class, () -> connect( 12345, null ) );
        assertThrows( IOException.class, () -> connect( 12345, listener ) );

        assertThrows( NullArgumentException.class, () -> connect( null, 12345, 23456, listener ) );
        assertThrows( MalformedURLException.class, () -> connect( "Fußpilz", 12345, 23456, listener ) );
        assertThrows( NullArgumentException.class, () -> connect( "hostname", 12345, 23456, null ) );
        assertThrows( IOException.class, () -> connect( "hostname", 12345, 23456, listener ) );
    }   //  testConnect()
}
//  class TestPerfLogRemote

/*
 *  End of File
 */