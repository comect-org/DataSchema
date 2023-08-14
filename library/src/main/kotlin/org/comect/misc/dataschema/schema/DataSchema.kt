/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSchema(
	val types: List<Type>,
	val settings: Settings
)

@Serializable
data class Type(
	val name: String,
	val attributes: List<Attribute> = listOf(),
)

@Serializable
data class Attribute(
	val type: String,

	val name: String? = null,
	val parameters: List<Attribute> = listOf()
)

@Serializable
data class Settings(
	@SerialName("package")
	val packageName: String,

	val languages: List<String>
)
