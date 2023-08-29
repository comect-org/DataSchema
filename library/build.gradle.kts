/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    `dataschema-module`
    `dokka-module`
    `published-module`
}

val projectVersion: String by project

group = "org.comect.misc"
version = projectVersion

repositories {
    mavenCentral()
}

metadata {
    name = "DataSchema"
    description = "Library for transforming data files into data class-equivalents in various languages"
}

dependencies {
    implementation(libs.kotlinx.cli)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.jte)

    implementation(libs.json5k)
    implementation(libs.kaml)
    implementation(libs.ktoml)
    implementation(libs.kotlinx.hocon)
    implementation(libs.bundles.xmlutil)

    implementation(libs.logging)
    implementation(libs.reflections)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

dokkaModule {
    includes.add("packages.md")
}
