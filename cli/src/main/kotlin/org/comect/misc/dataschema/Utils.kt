/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import io.github.oshai.kotlinlogging.KLogger
import kotlin.system.exitProcess

public fun KLogger.quit(code: Int = 1, message: () -> Any) {
	error(message)

	exitProcess(code)
}
