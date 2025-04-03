import java.util.Properties
import java.io.File

fun getLocalProperty(key: String): String? {
    val properties = Properties()
    val localProperties = File(rootDir, "local.properties")
    if (localProperties.isFile) {
        properties.load(localProperties.inputStream())
        return properties.getProperty(key)
    }
    return null
}

val spotlessPluginId = libs.plugins.spotless.get().pluginId
val ktlintVersion = libs.versions.ktlint.get()

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.vanniktech.maven.publish) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.compose.multiplatform) apply false
}


subprojects {
    apply {
        plugin(spotlessPluginId)
    }

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            targetExclude("bin/**/*.kt")

            ktlint("0.41.0").editorConfigOverride(
                mapOf(
                    "ktlint_standard_no-wildcard-imports" to "disabled",
                    "ktlint_standard_max-line-length" to "100",
                    "android" to "true"
                )
            )
        }
    }
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs = listOf("-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api")
        }
    }
}

