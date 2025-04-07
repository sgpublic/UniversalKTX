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
        namespace = "io.github.sgpublic.uniktx.forest"
        compileSdk = 35
        minSdk = 26

        withHostTest {

        }
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        jvmMain {
            dependencies {
                implementation(libs.forest)
                implementation(libs.gson)
            }
        }

        androidMain {
            dependencies {

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
