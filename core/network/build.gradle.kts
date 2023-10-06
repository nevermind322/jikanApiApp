
plugins {
    id("jikan.android.library")
    id ("jikan.android.hilt")
}

android {
    namespace = "com.example.network"

}

dependencies {
    implementation (libs.gson)
    implementation (libs.retrofit.converter.gson)
    implementation (libs.retrofit)
}
