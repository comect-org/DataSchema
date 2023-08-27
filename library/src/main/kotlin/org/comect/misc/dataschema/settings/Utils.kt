/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema.settings

import org.comect.misc.dataschema.getResources

private var languageCache: Map<String, LanguageSettings> = mutableMapOf()

fun getLanguages(reload: Boolean = false): Map<String, LanguageSettings> {
	if (reload || languageCache.isEmpty()) {
		val languages = getResources("languages")
			.filter { "." !in it }
			.map { it.trim('/') }

		val newCache = mutableMapOf<String, LanguageSettings>()

		languages.forEach { name ->
			val settings = LanguageSettings.load(name)

			if (settings != null) {
				newCache[settings.name] = settings
			}
		}

		languageCache = newCache
	}

	return languageCache
}
