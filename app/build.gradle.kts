plugins {
    id("jikan.android.application")
    id("jikan.android.hilt")
    id("jikan.android.appCompose")
}

android {
    namespace = "com.example.jikan"

    defaultConfig {
        applicationId = "com.example.jikan"
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
}

dependencies {

    implementation(libs.androidx.core.ktx )//.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(project(":core:model"))
    implementation(project(":core:design-system"))
    implementation(project(":feature:search"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
}
