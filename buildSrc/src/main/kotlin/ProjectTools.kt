/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import gradle.kotlin.dsl.accessors._753802a3ee7ce14b64e15006baca3631.ext
import org.gradle.api.Project

class MetadataBuilder {
    lateinit var name: String
    lateinit var description: String
}

class Launch4jMetadataBuilder {
    lateinit var outfile: String
    lateinit var mainClassName: String
    var headerType: String = "console"
}

fun Project.metadata(body: (MetadataBuilder).() -> Unit) {
    val builder = MetadataBuilder()

    body(builder)

    ext.set("pubName", builder.name)
    ext.set("pubDesc", builder.description)
}


fun Project.launch4jMeta(body: (Launch4jMetadataBuilder).() -> Unit) {
    val builder = Launch4jMetadataBuilder()

    body(builder)

    ext.set("l4jOutfile", builder.outfile)
    ext.set("l4jMainClassName", builder.mainClassName)
    ext.set("l4jHeaderType", builder.headerType)
}
