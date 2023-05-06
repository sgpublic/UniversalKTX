// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val androidVer = "8.0.1"
    id("com.android.application") version androidVer apply false
    id("com.android.library") version androidVer apply false

    val kotlinVer = "1.8.20"
    kotlin("android") version kotlinVer apply false
    kotlin("jvm") version kotlinVer apply false

    val publishing = "0.1.6"
    id("io.github.sgpublic.android-publish") version publishing apply false
    id("io.github.sgpublic.java-publish") version publishing apply false
}