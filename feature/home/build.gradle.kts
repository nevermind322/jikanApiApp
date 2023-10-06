plugins {
    id("jikan.android.library")
    id("jikan.android.hilt")
    id("jikan.android.compose")
}

android {
    namespace = "com.example.home"
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation (project(":feature:detail"))
}