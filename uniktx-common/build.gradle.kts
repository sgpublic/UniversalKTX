import com.android.build.api.dsl.androidLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.multiplatform.android.library)
}

kotlin {
    jvm {
        withSourcesJar()
    }

    androidLibrary {
        namespace = "io.github.sgpublic.uniktx.common"
        compileSdk = 35
        minSdk = 26

        withHostTest {

        }
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    listOf(
        macosArm64(),
        macosX64(),
        iosX64(),
        iosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "uniktxCommon"
        }
    }
    linuxArm64()
    linuxX64()
    mingwX64()

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.gson)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.core)
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.recyclerview)
                implementation(libs.androidx.localbroadcastmanager)
                implementation(libs.bundles.androidx.viewmodel)
            }
        }
        val androidHostTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
        val androidDeviceTest by getting {
            dependencies {
                implementation(libs.bundles.androidx.test)
            }
        }
    }
}
