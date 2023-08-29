/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.generation

import gg.jte.CodeResolver
import gg.jte.ContentType
import gg.jte.TemplateEngine
import gg.jte.output.StringOutput
import gg.jte.resolve.ResourceCodeResolver

public object Templates {
	private val resolver: CodeResolver = ResourceCodeResolver("org/comect/misc/dataschema/languages")
	private val engine: TemplateEngine = TemplateEngine.create(resolver, ContentType.Plain)

	public fun render(name: String, parameters: Map<String, Any?>? = null): String {
		val output = StringOutput()

		engine.render("$name.kte", parameters, output)

		return output.toString().trim()
	}
}
