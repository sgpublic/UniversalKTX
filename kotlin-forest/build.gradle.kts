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
    implementation(project(":kotlin-common"))

    // forest 声明式网络请求框架
    /* https://forest.dtflyx.com/pages/1.5.x/intro/ */
    api("com.dtflys.forest:forest-core:1.5.26")
    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
}