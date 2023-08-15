/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import kotlin.system.exitProcess

fun exit(message: String, code: Int = 1) {
	System.err.println(message)

	exitProcess(code)
}
