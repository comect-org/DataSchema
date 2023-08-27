/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
    application

    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("edu.sc.seis.launch4j") version "3.0.4"
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

    implementation(libs.logging)
    implementation(libs.bundles.log4j)

    implementation(project(":library"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass = "org.comect.misc.dataschema.MainKt"
}

tasks {
    jar {
        manifest {
            attributes(
                "Main-Class" to "org.comect.misc.dataschema.MainKt"
            )
        }
    }
}

license {
    rule(rootProject.file("LICENSE-HEADER"))

    exclude("**/*.kte")
    exclude("**/*.yaml")
}

launch4j {
    outfile = "DataSchema.exe"
    mainClassName = "org.comect.misc.dataschema.MainKt"
    headerType = "console"
    chdir.set("")

    jvmOptions.set(listOf("-Duser.dir=%OLDPWD%"))

    copyConfigurable.set(emptyArray<Any>())

    setJarTask(project.tasks.shadowJar.get())
}

tasks.build.get().dependsOn(tasks.applyLicenses)
tasks.build.get().dependsOn(tasks.createExe)
