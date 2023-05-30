plugins {
    id("java-library")
    kotlin("jvm")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.java-publish")
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
    /* https://github.com/square/okhttp */
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}