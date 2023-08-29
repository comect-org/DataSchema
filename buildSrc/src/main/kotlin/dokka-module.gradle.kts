/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

import java.net.URL

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
}

val javadocJar = task("javadocJar", Jar::class) {
    dependsOn("dokkaJavadoc")
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
    from(tasks.javadoc)
}

val dokkaModuleExtensionName = "dokkaModule"

abstract class DokkaModuleExtension {
    abstract val moduleName: Property<String>
    abstract val includes: ListProperty<String>
}

extensions.create<DokkaModuleExtension>(dokkaModuleExtensionName)

tasks {
    build {
        finalizedBy(javadocJar)
    }

    afterEvaluate {
        val projectDir = project.projectDir.relativeTo(rootProject.rootDir).toString()

        dokkaHtml {
            val extension = project.extensions.getByName<DokkaModuleExtension>(dokkaModuleExtensionName)

            extension.moduleName.orNull?.let {
                moduleName.set(it)
            }

            dokkaSourceSets {
                configureEach {
                    includeNonPublic.set(false)
                    skipDeprecated.set(false)
                    extension.moduleName.orNull?.let {
                        displayName.set(it)
                    }
                    extension.includes.orNull?.let {
                        includes.from(*it.toTypedArray())
                    }
                    jdkVersion.set(8)

                    sourceLink {
                        localDirectory.set(file("${project.projectDir}/src/main/kotlin"))

                        remoteUrl.set(
                            URL(
                                "https://github.com/comect-org/DataSchema/" +
                                    "tree/${getCurrentGitBranch()}/${projectDir}/src/main/kotlin"
                            )
                        )

                        remoteLineSuffix.set("#L")
                    }

                    externalDocumentationLink {
                        url.set(URL("http://kordlib.github.io/kord/common/common/"))
                    }

                    externalDocumentationLink {
                        url.set(URL("http://kordlib.github.io/kord/core/core/"))
                    }
                }
            }
        }
    }
}
