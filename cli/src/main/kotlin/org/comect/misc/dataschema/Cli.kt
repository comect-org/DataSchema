/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(ExperimentalCli::class)

package org.comect.misc.dataschema

import kotlinx.cli.*

object Cli {
	val parser = ArgParser("dataschema")

	init {
		parser.subcommands(Single, Directory)
	}

	fun parse(args: Array<String>) {
		parser.parse(args)
	}

	object Single : Subcommand("single", "Transform a single input file") {
		val input by argument(ArgType.String, description = "Input file")
		val output by argument(ArgType.String, description = "Output file")

		val outputFormats by option(
			ArgType.String,

			shortName = "of",
			description = "Input file formats, comma-separated"
		).required()

		val inputFormat by option(ArgType.String, shortName = "if", description = "Input file format")
			.default("auto")

		override fun execute() {
			TODO("Not yet implemented")
		}
	}

	object Directory : Subcommand("directory", "Transform an entire directory of input files") {
		val input by argument(ArgType.String, description = "Input directory")
		val output by argument(ArgType.String, description = "Output directory")

		val recursive by option(
			ArgType.Boolean, description = "Recurse into sub-folders"
		).default(false)

		val outputFormats by option(
			ArgType.String,

			shortName = "of",
			description = "Output file formats, comma-separated"
		).required()

		override fun execute() {
			TODO("Not yet implemented")
		}
	}
}
