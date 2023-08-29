/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    application

    `dataschema-module`
    `launch4j-module`
}

val projectVersion: String by project

group = "org.comect.misc"
version = projectVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.cli)
    implementation(libs.kotlinx.serialization)

    implementation(libs.logging)
    implementation(libs.bundles.log4j)

    implementation(project(":library"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

launch4jMeta {
    mainClassName = "org.comect.misc.dataschema.MainKt"
    outfile = "DataSchema.exe"
}
