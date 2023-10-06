plugins {
    id("jikan.android.library")

    id("jikan.android.compose")
    id("jikan.android.hilt")
}

android {
    namespace = "com.example.detail"
}

dependencies {

    implementation (libs.picasso)


    implementation(project(":core:data"))
    implementation(project(":core:model"))

}