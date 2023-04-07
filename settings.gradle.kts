pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "UniversalKTX"

include(":android-common")
include(":android-logback")
include(":kotlin-common")
include(":kotlin-forest")
include(":kotlin-logback")
include(":android-forest")
