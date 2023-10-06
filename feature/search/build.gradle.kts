plugins {
    id("jikan.android.library")
    id ("jikan.android.compose")
    id("jikan.android.hilt")

}

android {
    namespace = "com.example.search"
}

dependencies {


    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))

    implementation(project(":feature:home"))
}