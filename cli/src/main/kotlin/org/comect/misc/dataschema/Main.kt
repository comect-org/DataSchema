/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import io.github.oshai.kotlinlogging.KotlinLogging
import org.comect.misc.dataschema.parsing.getLanguages

fun main(args: Array<String>) {
	val logger = KotlinLogging.logger {}
	val languages = getLanguages(true)

	logger.info { "Loaded ${languages.size} languages: " + languages.keys.joinToString() }

	Cli.parse(args)
}
