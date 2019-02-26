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

import groovy.util.Node

plugins {
    id("org.jetbrains.kotlin.multiplatform").version("1.3.21")
    id("maven-publish")
    id("signing")
}
group = "com.github.hadilq"
version = "0.0.1"

kotlin {
    jvm()
//    js()
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

        js {
            compilations["main"].defaultSourceSet.dependencies {
                api(kotlin("stdlib-js"))
            }
            compilations["test"].defaultSourceSet.dependencies {
                implementation(kotlin("test-js"))
            }
        }
        val nativeMain by creating

        configure(listOf(linuxX64(), mingwX64(), macosX64())) {
            compilations["main"].defaultSourceSet.dependsOn(nativeMain)
        }
    }
}

publishing {
    repositories {
        maven(uri("$buildDir/repo"))
    }
}

// Publishing

//// Add a Javadoc JAR to each publication as required by Maven Central

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.value("javadoc")
    // TODO: instead of a single empty Javadoc JAR, generate real documentation for each module
}

publishing {
    publications.withType<MavenPublication>().all {
        artifact(javadocJar)
    }
}

//// The root publication also needs a sources JAR as it does not have one by default

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.value("sources")
}

publishing.publications.withType<MavenPublication>().getByName("kotlinMultiplatform").artifact(sourcesJar)

//// Customize the POMs adding the content required by Maven Central

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
                add("url", "https://github.com/hadilq/num4k/blob/master/LICENSE")
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
                add("name", "hadilq")
            }
        }
    }
}

publishing {
    publications.withType<MavenPublication>().all {
        customizeForMavenCentral(pom)
    }
}

//// Sign the publications:

////// Also requires that signing.keyId, signing.password, and signing.secretKeyRingFile are provided as Gradle
////// properties.

////// No complex signing configuration is required here, as the signing plugin interoperates with maven-publish
////// and can simply add the signature files directly to the publications:

publishing {
    publications.withType<MavenPublication>().all {
        signing.sign(this@all)
    }
}