pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}

rootProject.name = "animate-compose"
include(":animate-compose")
include(":sample:common")
include(":sample:android")
include(":sample:desktop")
include(":sample:web")
