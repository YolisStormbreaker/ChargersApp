// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.serialization") version "1.9.20"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    val navVersion = "2.7.5"
    val koinVersion = "3.4.0"
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}