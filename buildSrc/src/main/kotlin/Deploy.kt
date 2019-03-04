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
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.io.File
import java.util.*
import org.gradle.api.publish.maven.MavenPom

fun customizeForMavenCentral(pom: MavenPom) = pom.buildAsNode {
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

fun Node.add(key: String, value: String)
        = appendNode(key).setValue(value)

fun Node.node(key: String, content: Node.() -> Unit)
        = appendNode(key).also(content)

fun MavenPom.buildAsNode(builder: Node.() -> Unit)
        = withXml { asNode().apply(builder) }

fun ExtraPropertiesExtension.getOrNull(name: String)
        = if(has(name)) get(name) else null