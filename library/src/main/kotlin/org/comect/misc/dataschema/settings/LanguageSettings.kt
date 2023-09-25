/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.comect.misc.dataschema.getResource
import org.comect.misc.dataschema.schema.Attribute
import org.comect.misc.dataschema.schema.TypeParameter

@Serializable
@Suppress("DataClassContainsFunctions")
public data class LanguageSettings(
	val name: String,
	val extension: String,
	val types: Map<String, String>,
	val imports: Map<String, String> = mapOf(),

	@SerialName("nullable_prefix")
	val nullablePrefix: String = "",

	@SerialName("nullable_suffix")
	val nullableSuffix: String = "",
) {
	private fun getType(type: String) =
		types[type] ?: type

	public fun getType(type: Attribute): String {
		var typeString = getType(type.type)

		if (type.nullable) {
			typeString = "$nullablePrefix${typeString}$nullableSuffix"
		}

		type.parameters.forEachIndexed { index, param ->
			var innerString = getType(param)

			if (param.nullable) {
				innerString = "$nullablePrefix${innerString}$nullableSuffix"
			}

			typeString = typeString.replace(
				"$${index + 1}",
				innerString
			)
		}

		return typeString
	}

	public fun getType(type: TypeParameter): String {
		var typeString = getType(type.type)

		type.parameters.forEachIndexed { index, param ->
			typeString = typeString.replace(
				"$${index + 1}",
				getType(param)
			)
		}

		return typeString
	}

	public companion object {
		private val json = Json.Default

		public fun load(language: String): LanguageSettings? {
			val stream = getResource("languages/$language/settings.json")
				?: return null

			return json.decodeFromString<LanguageSettings>(stream.readBytes().decodeToString())
		}
	}
}
