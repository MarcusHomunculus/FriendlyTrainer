plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

// https://touchlab.co/understanding-and-configuring-your-kmm-test-suite/
// https://github.com/liauli/sqldelight-test/tree/master/shared/src/commonTest

kotlin {
    android()

    /*
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }
    */

    val sqlDelightVersion: String by project
    val junitVersion: String by project
    val coroutinesVersion: String by project

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation("org.junit.vintage:junit-vintage-engine:$junitVersion")
                implementation("androidx.test:core:1.4.0")
                implementation("org.robolectric:robolectric:4.8.1")
                implementation("org.mockito:mockito-core:4.6.1")
                implementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
            }
        }
        /*
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
        */
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26 // 23 -> requires desugaring
        targetSdk = 32
    }
}

sqldelight {
    database("AppStorage") {
        packageName = "com.github.friendlytrainer.storage"
    }
}
