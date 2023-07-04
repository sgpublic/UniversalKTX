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

    implementation(kotlin("reflect"))
    api("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.8")
}