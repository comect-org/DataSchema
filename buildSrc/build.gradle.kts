/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
	`kotlin-dsl`
}

repositories {
	google()
	gradlePluginPortal()
}

dependencies {
	implementation(kotlin("gradle-plugin", version = "1.9.10"))
	implementation(kotlin("serialization", version = "1.9.10"))

	implementation("com.github.ben-manes", "gradle-versions-plugin", "0.47.0")
	implementation("com.github.jakemarsden", "git-hooks-gradle-plugin", "0.0.2")
	implementation("com.github.johnrengelman", "shadow", "8.1.1")
	implementation("com.google.devtools.ksp", "com.google.devtools.ksp.gradle.plugin", "1.9.0-1.0.12")
	implementation("edu.sc.seis.launch4j", "launch4j", "3.0.4")
	implementation("io.github.gradle-nexus", "publish-plugin", "1.3.0")
	implementation("io.gitlab.arturbosch.detekt", "detekt-gradle-plugin", "1.23.1")
	implementation("org.jetbrains.dokka", "dokka-gradle-plugin", "1.8.20")
	implementation("org.quiltmc", "quilt-gradle-licenser", "2.0.1")

	implementation(gradleApi())
	implementation(localGroovy())
}
