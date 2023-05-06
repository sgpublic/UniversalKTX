plugins {
    id("java-library")
    kotlin("jvm")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.java-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":kotlin-common"))
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation(project(":kotlin-logback"))

    // forest 声明式网络请求框架
    /* https://forest.dtflyx.com/pages/1.5.x/intro/ */
    implementation("com.dtflys.forest:forest-core:1.5.26")
    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
}