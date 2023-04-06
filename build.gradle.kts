// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val androidVer = "7.4.2"
    id("com.android.application") version androidVer apply false
    id("com.android.library") version androidVer apply false

    val kotlinVer = "1.8.0"
    kotlin("android") version kotlinVer apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}