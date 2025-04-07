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
        namespace = "io.github.sgpublic.uniktx.logback"
        compileSdk = 35
        minSdk = 26

        withHostTest {

        }
        withDeviceTest {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.slf4j.api)
                implementation(libs.kotlin.reflect)
            }
        }

        jvmMain {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.logback.classic)
            }
        }

        androidMain {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.logback.android)
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
