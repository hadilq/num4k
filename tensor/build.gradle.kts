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
    kotlin("multiplatform") version "1.3.21"
    id("maven-publish")
    id("signing")
}

group = Versions.group
base.archivesBaseName = "num4k-tensor"
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
    androidNativeArm64()
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

val keyId = System.getenv()["SIGNING_KEYID"]
    ?: extra.getOrNull("signing.keyId") as String?
val gpgPassword = System.getenv()["SIGNING_PASSWORD"]
    ?: extra.getOrNull("signing.password") as String?
val gpgFile = System.getenv()["SIGNING_SECRETRINGFILE"]
    ?: extra.getOrNull("signing.secretKeyRingFile") as String?
    ?: if(file("./secret.gpg").exists()) file("./secret.gpg").absolutePath else null
val ossrhUsername = System.getenv()["OSSRHUSERNAME"]
    ?: extra.getOrNull("ossrhUsername") as String?
val ossrhPassword = System.getenv()["OSSRHPASSWORD"]
    ?: extra.getOrNull("ossrhPassword") as String?

if (listOf(
        keyId,
        gpgPassword,
        gpgFile,
        ossrhUsername,
        ossrhPassword
    ).none { it == null } && file(gpgFile!!).exists()
) {

    println("Publishing setup detected. Setting up publishing...")

    val javadocJar by tasks.creating(Jar::class) {
        archiveClassifier.value("javadoc")
        // TODO: instead of a single empty Javadoc JAR, generate real documentation for each module
    }

    val sourcesJar by tasks.creating(Jar::class) {
        archiveClassifier.value("sources")
    }

    extra["signing.keyId"] = keyId
    extra["signing.password"] = gpgPassword
    extra["signing.secretKeyRingFile"] = gpgFile

    publishing {
        publications {
            configure(withType<MavenPublication>()) {
                signing.sign(this)
                customizeForMavenCentral(pom)
                artifact(javadocJar)
            }
            withType<MavenPublication>()["kotlinMultiplatform"].artifact(sourcesJar)
        }
        repositories {
            maven(url = "https://oss.sonatype.org/service/local/staging/deploy/maven2")
                .credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                }
        }
    }
} else println(buildString {
    appendln("Not enough informatio+n to publish:")
    appendln("keyId: ${if (keyId == null) "NOT " else ""}found")
    appendln("gpgPassword: ${if (gpgPassword == null) "NOT " else ""}found")
    appendln("gpgFile: ${gpgFile ?: "NOT found"}")
    appendln("gpgFile presence: ${gpgFile?.let { file(it).exists() } ?: "false"}")
    appendln("ossrhUsername: ${if (ossrhUsername == null) "NOT " else ""}found")
    appendln("ossrhPassword: ${if (ossrhPassword == null) "NOT " else ""}found")
})

fun customizeForMavenCentral(pom: org.gradle.api.publish.maven.MavenPom) = pom.buildAsNode {
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
        add("url", "https://github.com")
        add("connection", "scm:git:git://github.com/hadilq/num4k.git")
        add("developerConnection", "scm:git:ssh://github.com/hadilq/num4k.git/lamba92/kotlin-extlib.git")
    }
    node("developers") {
        node("developer") {
            add("id", "hadilq")
            add("name", "Hadi Lashkari Ghouchani")
            add("email", "hadilq.dev@gmail.com")
        }
    }
}

fun KotlinNativeTarget.compilations(name: String, config: KotlinNativeCompilation.() -> Unit) =
    compilations[name].apply(config)

fun properties(file: File)
        = Properties().apply { load(file.apply { if (!exists()) createNewFile() }.inputStream()) }

fun properties(fileSrc: String)
        = properties(file(fileSrc))

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

fun Node.add(key: String, value: String)
        = appendNode(key).setValue(value)

fun Node.node(key: String, content: Node.() -> Unit)
        = appendNode(key).also(content)

fun org.gradle.api.publish.maven.MavenPom.buildAsNode(builder: Node.() -> Unit)
        = withXml { asNode().apply(builder) }

fun ExtraPropertiesExtension.getOrNull(name: String)
        = if(has(name)) get(name) else null