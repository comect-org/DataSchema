/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:Suppress("StringLiteralDuplication")

package org.comect.misc.dataschema

import kotlinx.serialization.decodeFromString
import org.comect.misc.dataschema.generation.Templates
import org.comect.misc.dataschema.generation.TypeContainer
import org.comect.misc.dataschema.generation.VariableContainer
import org.comect.misc.dataschema.schema.Attribute
import org.comect.misc.dataschema.schema.DataSchema
import org.comect.misc.dataschema.schema.DataTypes
import org.comect.misc.dataschema.schema.TypeParameter
import org.comect.misc.dataschema.settings.LanguageSettings
import org.comect.misc.dataschema.settings.getLanguages
import java.io.File

private var languages: Map<String, LanguageSettings>? = null

public fun generate(file: File): Map<LanguageSettings, Map<String, String>> {
	val type = DataTypes.getType(file.extension)
		?: error("Unknown file extension: ${file.extension}")

	return generate(file.readText(), type)
}

public fun generate(string: String, type: DataTypes): Map<LanguageSettings, Map<String, String>> =
	generate(
		type.serializer.decodeFromString<DataSchema>(string)
	)

public fun generate(schema: DataSchema): Map<LanguageSettings, Map<String, String>> {
	if (languages == null) {
		languages = getLanguages(true)
	}

	val result: MutableMap<LanguageSettings, MutableMap<String, String>> = mutableMapOf()

	schema.settings.languages.forEach { languageName ->
		val language = languages!![languageName]
			?: error("Unknown language: $languageName")

		result[language] = mutableMapOf()

		schema.types.forEach { type ->
			val imports = type.attributes.map { collectImports(it, language) }.flatten()

			val typeComment = if (type.comment != null) {
				Templates.render(
					"$languageName/comment",
					mapOf("comment" to type.comment.lines().filter { it.trim().isNotEmpty() })
				)
			} else {
				null
			}

			val variables = type.attributes.map { variable ->
				val variableComment = if (variable.comment != null) {
					Templates.render(
						"$languageName/comment",
						mapOf("comment" to variable.comment.lines().filter { it.trim().isNotEmpty() })
					)
				} else {
					null
				}

				VariableContainer(
					attribute = variable,
					comment = variableComment,
					line = generateLine(variable, languageName, language)
				)
			}

			val typeContainer = TypeContainer(
				imports = imports,
				name = type.name,
				packageName = schema.settings.packageName,
				comment = typeComment,
				variables = variables,
			)

			result[language]!![type.name] = Templates.render(
				"$languageName/file",
				mapOf(
					"comment" to typeContainer.comment,
					"imports" to typeContainer.imports,
					"name" to typeContainer.name,
					"packageName" to typeContainer.packageName,
					"variables" to typeContainer.variables
				)
			)
		}
	}

	return result
}

public fun generateLine(variable: Attribute, languageName: String, language: LanguageSettings): String {
	val typeString = language.getType(variable)

	return Templates.render(
		"$languageName/variable",

		mapOf(
			"name" to variable.name,
			"type" to typeString,
			"constant" to variable.constant
		)
	)
}

public fun collectImports(type: Attribute, language: LanguageSettings): List<String> {
	val imports = mutableListOf(language.imports[type.type])

	if (type.parameters.isNotEmpty()) {
		type.parameters.map {
			imports.addAll(collectImports(it, language))
		}
	}

	return imports.filterNotNull()
}

public fun collectImports(type: TypeParameter, language: LanguageSettings): List<String?> {
	val imports = mutableListOf(language.imports[type.type])

	if (type.parameters.isNotEmpty()) {
		type.parameters.map {
			imports.addAll(collectImports(it, language))
		}
	}

	return imports.filterNotNull()
}
