/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

public object Utils {
	public fun splitFirst(str: String, char: Char): String =
		str.split(char, limit = 2).first()

	public fun splitLast(str: String, char: Char): String =
		str.split(char).last()

	public fun splitFirstLast(str: String, firstChar: Char, lastChar: Char): String =
		splitFirst(
			splitLast(str, lastChar),
			firstChar
		)

	public fun splitLastFirst(str: String, firstChar: Char, lastChar: Char): String =
		splitLast(
			splitFirst(str, firstChar),
			lastChar
		)
}
