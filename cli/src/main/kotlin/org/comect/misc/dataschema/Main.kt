/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import io.github.oshai.kotlinlogging.KotlinLogging

fun main(args: Array<String>) {
	val logger = KotlinLogging.logger{}

	logger.debug { "Test message!" }
	logger.info { "Test message!" }
	logger.warn { "Test message!" }
	logger.error { "Test message!" }

	Cli.parse(args)
}
