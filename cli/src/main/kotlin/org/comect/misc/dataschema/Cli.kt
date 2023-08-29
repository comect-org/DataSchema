/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgParserResult
import kotlinx.cli.ArgType
import kotlinx.cli.default

public object Cli {
	private val parser = ArgParser("dataschema")

	public val input: String by parser
		.argument(ArgType.String, description = "Input file")

	public val output: String by parser
		.option(
			ArgType.String,
			shortName = "o",
			description = "Output folder, will be created if it doesn't exist"
		).default("out")

	public val overwrite: Boolean by parser.option(
		ArgType.Boolean,
		shortName = "ow",
		description = "Overwrite existing files"
	).default(false)

	public fun parse(args: Array<String>): ArgParserResult =
		parser.parse(args)
}
