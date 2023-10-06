plugins {
    id("jikan.android.library")
    id ("jikan.android.hilt")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation (libs.retrofit)
    implementation (libs.androidx.paging)
}