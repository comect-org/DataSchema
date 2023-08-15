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
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.StringFormat
import kotlinx.serialization.hocon.Hocon
import kotlinx.serialization.json.Json
import org.comect.misc.dataschema.schema.DataType.Companion.instances
import nl.adaptivity.xmlutil.serialization.XML as Xml

sealed class DataType(val name: String, serializer: SerialFormat, vararg val extensions: String) {
	data object HOCON : DataType("HOCON", Hocon.Default, "conf", "hocon")
	data object JSON : DataType("JSON", Json.Default, "json")
	data object JSON5 : DataType("JSON5", Json5.Default, "json5")
	data object TOML : DataType("TOML", Toml.Default, "toml")
	data object XML : DataType("XML", Xml.defaultInstance, "xml")
	data object YAML : DataType("YAML", Yaml.default, "yml", "yaml")

	companion object {
		val instances = arrayOf(HOCON, JSON, JSON5, TOML, XML, YAML)
	}
}

fun DataType.get(extension: String) =
	instances.firstOrNull { extension.lowercase() in extensions }
