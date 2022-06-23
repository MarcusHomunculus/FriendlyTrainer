plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.github.friendlytrainer.android"
        minSdk = 26 // 23
        targetSdk = 32
        versionCode = 1
        versionName = "0.0.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    val ktxLifecycleVersion: String by project
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment-ktx:1.4.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.androidplot:androidplot-core:1.5.9")
}