plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.parcelize")

    id("maven-publish")
    id("signing")
    id("io.github.sgpublic.android-publish")
}

android {
    namespace = "io.github.sgpublic.android.activity"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
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
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    api(project(":android-common"))
    implementation(project(":kotlin-logback")) {
        exclude("ch.qos.logback", "logback-classic")
    }
    implementation("org.slf4j:slf4j-api:2.0.7")

    implementation("androidx.activity:activity-compose:1.3.1")
    val compose = "1.0.5"
    implementation("androidx.compose.foundation:foundation:$compose")
    implementation("androidx.compose.material:material-ripple:$compose")
}