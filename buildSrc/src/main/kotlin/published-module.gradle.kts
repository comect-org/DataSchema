/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
	`maven-publish`
	signing
}

val sourceJar: Task by tasks.getting
val javadocJar: Task by tasks.getting

afterEvaluate {
	publishing {
		publications {
			create<MavenPublication>("mavenJava") {
				from(components.getByName("java"))

				artifact(sourceJar)
				artifact(javadocJar)

				pom {
					name.set(project.ext.get("pubName").toString())
					description.set(project.ext.get("pubDesc").toString())

					url.set("https://comect.org")

					packaging = "jar"

					scm {
						connection.set("scm:git:https://github.com/comect-org/DataSchema.git")
						developerConnection.set("scm:git:git@github.com:comect-org/DataSchema.git")
						url.set("https://github.com/comect-org/DataSchema.git")
					}

					licenses {
						license {
							name.set("Mozilla Public License Version 2.0")
							url.set("https://www.mozilla.org/en-US/MPL/2.0/")
						}
					}

					developers {
						developer {
							id.set("comect-org")
							name.set("Comect.org")
						}
					}
				}
			}
		}
	}

	signing {
		val signingKey: String? by project ?: return@signing
		val signingPassword: String? by project ?: return@signing

		useInMemoryPgpKeys(signingKey, signingPassword)

		sign(publishing.publications["mavenJava"])
	}
}
