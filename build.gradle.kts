buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    val sqlDelightVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.squareup.sqldelight:gradle-plugin:$sqlDelightVersion")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}