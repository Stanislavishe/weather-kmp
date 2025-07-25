import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared-domain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {

            // Ktor
            api(libs.ktor.client.core)
            api(libs.ktor.client.okhttp)
            api(libs.ktor.client.logging)
            api(libs.ktor.client.negotiation)
            api(libs.ktor.serialization)

            //Room
            api(libs.room.runtime)
            api(libs.room.gradle)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

}
android {
    namespace = "com.multrm.shared.domain"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    add("kspCommonMainMetadata", "androidx.room:room-compiler:2.7.2")
}