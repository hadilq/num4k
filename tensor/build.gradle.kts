import groovy.util.Node

/**
 * Copyright 2019 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("org.jetbrains.kotlin.multiplatform").version("1.3.21")
    id("maven-publish")
    id("signing")
}

group = findProperty("packagename") as String
version = findProperty("version") as String

kotlin {
    jvm()
    mingwX64()
    linuxX64()
    macosX64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        jvm {
            compilations["main"].defaultSourceSet.dependencies {
                api(kotlin("stdlib-jdk8"))
            }
            compilations["test"].defaultSourceSet.dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val nativeMain by creating

        configure(listOf(linuxX64(), mingwX64(), macosX64())) {
            compilations["main"].defaultSourceSet.dependsOn(nativeMain)
        }
    }
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.value("javadoc")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.value("sources")
}

publishing {
    repositories {
        maven(uri("$buildDir/repo"))

        val ossrhUsername = findProperty("ossrhUsername") as String?
        val ossrhPassword = findProperty("ossrhPassword") as String?

        if (ossrhUsername != null && ossrhPassword != null) {

            maven {
                name = "repository"
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }

            maven {
                name = "snapshotRepository"
                url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
            }
        }
    }

    publications.withType<MavenPublication>().all {
        signing.sign(this@all)

        artifact(javadocJar)

        customizeForMavenCentral(pom)
    }

    publications.withType<MavenPublication>().getByName("kotlinMultiplatform").artifact(sourcesJar)

}

fun customizeForMavenCentral(pom: org.gradle.api.publish.maven.MavenPom) = pom.withXml {
    fun Node.add(key: String, value: String) {
        appendNode(key).setValue(value)
    }

    fun Node.node(key: String, content: Node.() -> Unit) {
        appendNode(key).also(content)
    }

    asNode().run {
        add("description", "Modularized, mathematical and multiplatform Kotlin library")
        add("name", "Num4K")
        add("url", "https://github.com/hadilq/num4k")
        node("organization") {
            add("name", "com.github.hadilq")
            add("url", "https://github.com/hadilq")
        }
        node("issueManagement") {
            add("system", "github")
            add("url", "https://github.com/hadilq/num4k/issues")
        }
        node("licenses") {
            node("license") {
                add("name", "Apache License 2.0")
                add("url", "http://www.apache.org/licenses/LICENSE-2.0.txt")
                add("distribution", "repo")
            }
        }
        node("scm") {
            add("url", "https://github.com/hadilq/num4k")
            add("connection", "scm:git:git://github.com/hadilq/num4k.git")
            add("developerConnection", "scm:git:ssh://github.com/hadilq/num4k.git")
        }
        node("developers") {
            node("developer") {
                add("id", "hadilq")
                add("name", "Hadi Lashkari Ghouchani")
                add("email", "hadilq.dev@gmail.com")
            }
        }
    }
}