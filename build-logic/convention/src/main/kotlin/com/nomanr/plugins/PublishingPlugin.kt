package com.nomanr.plugins

import com.android.build.api.dsl.LibraryExtension
import com.android.tools.r8.internal.id
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType

open class PublishingPluginExtension {
    var publishGroupId: String = "com.nomanr"
    var publishVersion: String = "0.0.1"
    var artifactId: String = "animated-compose"
    var githubUrl: String = "github.com/nomanr/composables"
    var developerId: String = "nomanr"
    var developerName: String = "Noman R"
    var developerEmail: String = "hello@nomanr.com"
}

class PublishingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("com.vanniktech.maven.publish")

        val extension = project.extensions.create<PublishingPluginExtension>("publishingPlugin")

        project.afterEvaluate {
            configureMavenPublishing(project, extension)
        }
    }

    private fun configureMavenPublishing(project: Project, extension: PublishingPluginExtension) {
        project.extensions.configure(MavenPublishBaseExtension::class.java) {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
            signAllPublications()

            coordinates(extension.publishGroupId, extension.artifactId.ifEmpty { project.name }, extension.publishVersion)

            pom {
                name.set("Animate Compose")
                description.set("A collection of animations for Compose.")
                url.set("https://${extension.githubUrl}")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set(extension.developerId)
                        name.set(extension.developerName)
                        email.set(extension.developerEmail)
                    }
                }

                scm {
                    connection.set("scm:git:https://${extension.githubUrl}.git")
                    developerConnection.set("scm:git:ssh://${extension.githubUrl}.git")
                    url.set("https://${extension.githubUrl}")
                }
            }
        }
    }
}
