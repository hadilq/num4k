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
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.util.Properties

plugins {
    kotlin("multiplatform") version Versions.kotlinVersion
    id("maven-publish")
    id("signing")
}

group = Versions.group
base.archivesBaseName = "num4k-number"
version = Versions.version

kotlin {

    sourceSets.create("nativeCommon")

    jvm()
    js()
    // wasm32()
    iosArm64()
    iosX64()
    mingwX64()
    macosX64()
    linuxX64()
    linuxArm32Hfp()
    linuxMips32()
    linuxMipsel32()
//    androidNativeArm64()
    metadata {
        mavenPublication {
            artifactId = "kotlin-extlib-metadata"
        }
    }

    configure(nativeTargets) {
        compilations("main") {
            defaultSourceSet.dependsOn(sourceSets["nativeCommon"])
            outputKinds(DYNAMIC)
            cinterops.create("nativeMutex") {
                includeDirs(buildDir)
            }
        }
    }

    configure(platformIndependentTargets + androidTargets) {
        mavenPublication {
            tasks.withType<AbstractPublishToMaven>().all {
                onlyIf {
                    publication != this@mavenPublication || OperatingSystem.current().isLinux
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.() -> Unit) =
    compilations[name].apply(config)

val KotlinMultiplatformExtension.nativeTargets
    get() = targets.filter { it is KotlinNativeTarget }.map { it as KotlinNativeTarget }

val KotlinMultiplatformExtension.platformIndependentTargets
    get() = targets.filter { it !is KotlinNativeTarget || it.konanTarget == KonanTarget.WASM32 }

val KotlinMultiplatformExtension.appleTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.IOS_ARM64,
            KonanTarget.IOS_X64,
            KonanTarget.MACOS_X64
        ).any { target -> it.konanTarget == target }
    }

val KotlinMultiplatformExtension.windowsTargets
    get() = targets.filter { it is KotlinNativeTarget && it.konanTarget == KonanTarget.MINGW_X64 }

val KotlinMultiplatformExtension.linuxTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.LINUX_ARM32_HFP,
            KonanTarget.LINUX_MIPS32,
            KonanTarget.LINUX_MIPSEL32,
            KonanTarget.LINUX_X64
        ).any { target -> it.konanTarget == target }
    }

val KotlinMultiplatformExtension.androidTargets
    get() = targets.filter {
        it is KotlinNativeTarget && listOf(
            KonanTarget.ANDROID_ARM32,
            KonanTarget.ANDROID_ARM64
        ).any { target -> it.konanTarget == target }
    }