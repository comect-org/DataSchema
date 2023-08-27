/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.default

object Cli {
	private val parser = ArgParser("dataschema")

	val input by parser
		.argument(ArgType.String, description = "Input file")

	val output by parser
		.option(
			ArgType.String,
			shortName = "o",
			description = "Output folder, will be created if it doesn't exist"
		).default("out")

	val overwrite by parser.option(
		ArgType.Boolean,
		shortName = "ow",
		description = "Overwrite existing files"
	).default(false)

	fun parse(args: Array<String>) =
		parser.parse(args)
}
