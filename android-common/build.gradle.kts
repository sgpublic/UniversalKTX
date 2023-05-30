plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.parcelize")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.android-publish")
}

android {
    namespace = "io.github.sgpublic.android.common"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    api(project(":kotlin-common"))
    api(project(":kotlin-logback"))
    implementation("org.slf4j:slf4j-api:2.0.7")

    implementation("androidx.databinding:viewbinding:8.0.2")

    val lifecycle = "2.4.0"
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle")

    /* https://github.com/yanzhenjie/Sofia */
    implementation("com.yanzhenjie:sofia:1.0.5")
}