plugins {
    id("com.android.application")
    id("de.mannodermaus.android-junit5")
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

    testOptions {
        junitPlatform {
            filters {
                excludeTags("integration")
                includeTags("unittest")
            }

            debugFilters {
                includeTags("integration")
            }
        }
    }
}

dependencies {
    val coroutinesVersion: String by project
    val ktxLifecycleVersion: String by project
    val junitVersion: String by project
    implementation(project(":shared"))
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$ktxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.fragment:fragment-ktx:1.5.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.androidplot:androidplot-core:1.5.9")
    implementation("com.google.android.material:material:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$$junitVersion")
    testImplementation("com.jraska.livedata:testing-ktx:1.2.0")
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
}