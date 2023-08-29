/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm")
	kotlin("plugin.serialization")

	id("io.gitlab.arturbosch.detekt")
	id("org.quiltmc.gradle.licenser")
}

val sourceJar = task("sourceJar", Jar::class) {
	dependsOn(tasks["classes"])
	archiveClassifier.set("sources")
	from(sourceSets.main.get().allSource)
}

tasks {
	build {
		dependsOn(tasks.applyLicenses)
		finalizedBy(sourceJar)
	}

	kotlin {
		explicitApi()
		jvmToolchain(8)
	}

	jar {
		from(rootProject.file("build/LICENSE-dataschema"))
	}

	afterEvaluate {
		rootProject.file("LICENSE").copyTo(rootProject.file("build/LICENSE-dataschema"), true)

		tasks.withType<JavaCompile>().configureEach {
			sourceCompatibility = "1.8"
			targetCompatibility = "1.8"
		}

		withType<KotlinCompile>().configureEach {
			compilerOptions {
				freeCompilerArgs.add("-Xallow-kotlin-package")
			}

			kotlinOptions {
				jvmTarget = "1.8"
			}
		}
	}
}

dependencies {
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.1")
	detektPlugins("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.23.1")
}

detekt {
	buildUponDefaultConfig = true
	config.from(files("$rootDir/detekt.yml"))

	autoCorrect = true
}

license {
	rule(rootProject.file("LICENSE-HEADER"))

	exclude("**/*.kte")
	exclude("**/*.yaml")
	exclude("**/*.json")
}
