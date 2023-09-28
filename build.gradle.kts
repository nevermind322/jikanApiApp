import org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
    }
}

plugins {
    id ("com.android.application") version "8.1.1" apply false
    id ("com.android.library") version "8.1.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id ("com.google.dagger.hilt.android") version "2.44" apply false
    id ("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}

allprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            apiVersion = "1.8"
            languageVersion = "1.8"
        }
    }
}