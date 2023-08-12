/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.parsing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.comect.misc.dataschema.getResource

@Serializable
data class LanguageSettings(
	val name: String,
	val types: Map<String, String>,

	@SerialName("nullable_prefix")
	val nullablePrefix: String? = null,

	@SerialName("nullable_suffix")
	val nullableSuffix: String? = null,
) {
	companion object {
		private val json = Json.Default

		fun load(language: String): LanguageSettings? {
			val stream = getResource("languages/$language/settings.json")
				?: return null

			return json.decodeFromString<LanguageSettings>(stream.readBytes().decodeToString())
		}
	}
}
