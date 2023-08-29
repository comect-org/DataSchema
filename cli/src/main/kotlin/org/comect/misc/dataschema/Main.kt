/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import io.github.oshai.kotlinlogging.KotlinLogging
import org.comect.misc.dataschema.schema.DataTypes
import org.comect.misc.dataschema.settings.getLanguages
import java.io.File

public fun main(args: Array<String>) {
	val logger = KotlinLogging.logger {}
	val languages = getLanguages(true)

	logger.info { "Loaded ${languages.size} languages: " + languages.keys.sorted().joinToString() }

	Cli.parse(args)

	if (Cli.overwrite) {
		logger.warn { "Overwriting files in output directory!" }
	}

	val inputFile = File(Cli.input)

	if (!inputFile.exists() || !inputFile.isFile) {
		logger.quit { "Input file \"${Cli.input}\" does not exist or is not a file" }
	}

	val outputFolder = File(Cli.output)

	if (outputFolder.isFile) {
		logger.quit { "Output directory \"${Cli.output}\" appears to be a file - only directories are supported" }
	}

	if (!outputFolder.exists()) {
		outputFolder.mkdir()
	}

	if (!Cli.overwrite && outputFolder.listFiles()!!.isNotEmpty()) {
		logger.quit { "Output directory \"${Cli.output}\" contains files and --overwrite was not specified" }
	}

	logger.info { "Loading schema..." }

	val type = DataTypes.getType(inputFile.extension)

	if (type == null) {
		logger.quit {
			"Unknown input file extension \".${inputFile.extension}\" - supported extensions: " +
					DataTypes.extensions.joinToString { ".$it" }
		}
	}

	val result = generate(inputFile.readText(), type!!)

	result.forEach { (language, outputTypes) ->
		outputTypes.forEach { (name, string) ->
			val file = outputFolder.resolve("$name.${language.extension}")

			file.writeText(string)

			logger.info { "File written: \"${file.path}\"" }
		}
	}
}
