/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

@file:OptIn(ExperimentalSerializationApi::class)

package org.comect.misc.dataschema.schema

import com.akuleshov7.ktoml.Toml
import com.charleskorn.kaml.Yaml
import io.github.xn32.json5k.Json5
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML as Xml

public sealed class DataTypes(
	public val name: String,
	public val serializer: StringFormat,
	public vararg val extensions: String
) {
	public data object JSON : DataTypes("JSON", Json.Default, "json")
	public data object JSON5 : DataTypes("JSON5", Json5.Default, "json5")
	public data object TOML : DataTypes("TOML", Toml.Default, "toml")
	public data object XML : DataTypes("XML", Xml.defaultInstance, "xml")
	public data object YAML : DataTypes("YAML", Yaml.default, "yml", "yaml")

	public companion object {
		public val instances: Array<DataTypes> = arrayOf(JSON, JSON5, TOML, XML, YAML)
		public val extensions: List<String> = instances.map { it.extensions }.toTypedArray().flatten().sorted()

		public fun getType(extension: String): DataTypes? =
			instances.firstOrNull { extension.lowercase() in it.extensions }
	}
}
