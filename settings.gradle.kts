pluginManagement {
    includeBuild("build-logic")

    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "jikan"
include (":app")
include (":feature:search")
include (":core:model")
include (":core:data")
include (":core:network")
include (":core:database")
include (":core:design-system")
include (":feature:detail")
include (":feature:home")
