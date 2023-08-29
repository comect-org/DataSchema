/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DataSchema(
	val types: List<Type>,
	val settings: Settings
)

@Serializable
public data class Type(
	val name: String,
	val comment: String? = null,
	val attributes: List<Attribute> = listOf(),
)

@Serializable
public data class Attribute(
	val type: String,
	val name: String,
	val comment: String? = null,

	val constant: Boolean? = null,
	val parameters: List<TypeParameter> = listOf(),
)

@Serializable
public data class TypeParameter(
	val type: String,
	val parameters: List<TypeParameter> = listOf(),
)

@Serializable
public data class Settings(
	@SerialName("package")
	val packageName: String,

	val languages: List<String>,
)
