plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("jikan.android.hilt")
    id("jikan.android.appCompose")
}

android {
    namespace = "com.example.jikan"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.jikan"

        minSdk = 24
        targetSdk = 33

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        /*ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }*/
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation(project(":core:model"))
    implementation(project(":core:design-system"))
    implementation(project(":feature:search"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
}
