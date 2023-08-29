/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

plugins {
	application

	kotlin("jvm")

	id("com.github.johnrengelman.shadow")
	id("edu.sc.seis.launch4j")
}

tasks {
	build {
		dependsOn(tasks.createExe)
	}

	afterEvaluate {
		application {
			mainClass = project.ext.get("l4jMainClassName").toString()
		}

		jar {
			manifest {
				attributes(
					"Main-Class" to project.ext.get("l4jMainClassName").toString()
				)
			}
		}

		launch4j {
			outfile = project.ext.get("l4jOutfile").toString()
			mainClassName = project.ext.get("l4jMainClassName").toString()
			headerType = project.ext.get("l4jHeaderType").toString()

			chdir.set("")
			jvmOptions.set(listOf("-Duser.dir=%OLDPWD%"))
			copyConfigurable.set(emptyArray<Any>())

			setJarTask(tasks.getByName("shadowJar"))
		}
	}
}
