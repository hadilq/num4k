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