/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import org.reflections.Reflections
import org.reflections.scanners.Scanners
import java.io.InputStream

const val BASE_PATH = "org/comect/misc/dataschema/"

private val reflections = Reflections(
	BASE_PATH.replace("/", ".").trim('.'),
	Scanners.Resources
)

private val allResources = reflections.getResources(".*").associateWith {
	object {}.javaClass.getResource("/$it")
}

/**
 * Retrieve a specific resource at the given path, within the `org.comect.misc.dataschema` package.
 */
fun getResource(path: String): InputStream? {
	val actualPath = BASE_PATH + path

	val resourceURL = allResources[actualPath]
		?: return null

	return resourceURL.openStream()
}

/**
 * Retrieve a list of resources at the given base path, within the `org.comect.misc.dataschema` package.
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun getResources(path: String = "", recursive: Boolean = false): List<String> {
	val prefix = BASE_PATH + path

	var paths = allResources
		.filter { it.key.startsWith(prefix) }
		.map {
			it.key.removePrefix(prefix)
				.replace("\\", "/")
				.trim('/')
		}

	if (!recursive) {
		paths = paths
			.map {
				if ("/" in it) {
					it.split("/", limit = 2).first() + "/"
				} else {
					it
				}
			}
			.toSet()
			.toList()
	}

	// This is silly but required by the type system.
	return paths
}

/**
 * Retrieve a list of resources at the given base path, within the `org.comect.misc.dataschema` package, filtering it
 * based on the given [filter].
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun filterResources(path: String = "", recursive: Boolean = false, filter: String.() -> Boolean): List<String> {
	val resources = getResources(path, recursive)

	return resources.filter(filter)
}

/**
 * Call the given [body] with each resource at the given base path, within the `org.comect.misc.dataschema` package.
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun withResources(path: String = "", recursive: Boolean = false, body: String.() -> Unit) {
	val resources = getResources(path, recursive)

	resources.map(body)
}
