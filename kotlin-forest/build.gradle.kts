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
    api(project(":kotlin-common"))
    api(project(":kotlin-logback"))
    implementation("org.slf4j:slf4j-api:2.0.7")

    // forest 声明式网络请求框架
    /* https://forest.dtflyx.com/pages/1.5.x/intro/ */
    implementation("com.dtflys.forest:forest-core:1.5.31")
    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
}