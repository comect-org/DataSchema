/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.generation

import kotlinx.serialization.Serializable
import org.comect.misc.dataschema.schema.Attribute

@Serializable
public data class TypeContainer(
	val imports: List<String> = listOf(),
	val comment: String? = null,
	val name: String,
	val packageName: String,
	val variables: List<VariableContainer>,
)

@Serializable
public data class VariableContainer(
	val attribute: Attribute,
	val line: String,
	val comment: String? = null,
)
