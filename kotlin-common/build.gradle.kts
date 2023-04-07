plugins {
    id("java-library")
    kotlin("jvm")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.java-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
}