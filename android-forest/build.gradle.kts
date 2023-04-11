plugins {
    id("com.android.library")
    kotlin("android")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.android-publish")
}

android {
    namespace = "io.github.sgpublic.android.forest"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation(project(":android-common"))
    // forest 声明式网络请求框架
    /* https://forest.dtflyx.com/pages/1.5.x/intro/ */
    implementation("com.dtflys.forest:forest-core:1.5.26")
    /* https://github.com/google/gson */
    implementation("com.google.code.gson:gson:2.10.1")
    api(project(":kotlin-forest"))
}