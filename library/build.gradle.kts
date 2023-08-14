/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"

    id("org.quiltmc.gradle.licenser") version "2.0.1"
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

    implementation(libs.json5k)
    implementation(libs.kaml)
    implementation(libs.ktoml)
    implementation(libs.bundles.xmlutil)

    implementation(libs.logging)
    implementation(libs.reflections)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

license {
    rule(rootProject.file("LICENSE-HEADER"))

    exclude("**/*.kte")
    exclude("**/*.yaml")
}

tasks.build.get().dependsOn(tasks.applyLicenses)
