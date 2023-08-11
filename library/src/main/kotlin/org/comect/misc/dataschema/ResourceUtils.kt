/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package org.comect.misc.dataschema

import java.io.File

const val BASE_PATH = "org/comect/misc/dataschema/"

/**
 * Retrieve a specific resource at the given path, within the `org.comect.misc.dataschema` package.
 */
fun getResource(path: String): File? {
	val actualPath = BASE_PATH + path
	val resourceURL = ::getResource.javaClass.getResource(actualPath)
		?: return null

	return File(resourceURL.file)
}

/**
 * Retrieve a list of resources at the given base path, within the `org.comect.misc.dataschema` package.
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun getResources(path: String = "", recursive: Boolean = false): Map<String, File> {
	val prefix = BASE_PATH + path
	val resourceURL = ::getResources.javaClass.getResource(prefix)
	val directory = File(checkNotNull(resourceURL) { "Path not found: '$path'" }.file)

	return with(directory) {
		walk()
			.let {
				if (!recursive) {
					it.maxDepth(1)
				} else {
					it
				}
			}
			.map { it.path to it }
			.toMap()
	}
}

/**
 * Retrieve a list of resources at the given base path, within the `org.comect.misc.dataschema` package, filtering it
 * based on the given [filter].
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun filterResources(path: String = "", recursive: Boolean = false, filter: File.() -> File?): Map<String, File> {
	val prefix = BASE_PATH + path
	val resourceURL = ::filterResources.javaClass.getResource(prefix)
	val directory = File(checkNotNull(resourceURL) { "Path not found: '$path'" }.file)

	return with(directory) {
		walk()
			.let {
				if (!recursive) {
					it.maxDepth(1)
				} else {
					it
				}
			}
			.mapNotNull(filter)
			.map { it.path to it }
			.toMap()
	}
}

/**
 * Call the given [body] with each resource at the given base path, within the `org.comect.misc.dataschema` package.
 *
 * @param path Path prefix to search within
 * @param recursive Whether to recurse into subdirectories
 */
fun withResources(path: String = "", recursive: Boolean = false, body: File.() -> Unit) {
	val prefix = BASE_PATH + path
	val resourceURL = ::withResources.javaClass.getResource(prefix)
	val directory = File(checkNotNull(resourceURL) { "Path not found: '$path'" }.file)

	with(directory) {
		walk()
			.let {
				if (!recursive) {
					it.maxDepth(1)
				} else {
					it
				}
			}
			.map(body)
	}
}
