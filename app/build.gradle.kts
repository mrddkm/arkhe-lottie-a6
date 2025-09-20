import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.io.FileInputStream
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Properties
import java.util.TimeZone

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose)
    alias(libs.plugins.google.ksp)
}

val buildProperties = Properties()
val buildPropertiesFile = rootProject.file("build.properties")
if (buildPropertiesFile.exists()) {
    buildProperties.load(FileInputStream(buildPropertiesFile))
}
val appVersionCode = buildProperties.getProperty("versionCode", "1").toInt()
val appVersionName: String? = buildProperties.getProperty("versionName", "1.0.0")
val appProductName: String? = buildProperties.getProperty("productName", "arkhe")
val now: Instant? = Clock.systemUTC().instant()
val localDateTime: LocalDateTime? = now?.atZone(TimeZone.getDefault().toZoneId())?.toLocalDateTime()
val timestampFormatter: DateTimeFormatter? = DateTimeFormatter.ofPattern("yyMMddHHmm")
val timestampString = localDateTime?.format(timestampFormatter)
val buildTimestamp = timestampString

android {
    namespace = "com.arkhe.lottie"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.arkhe.lottie"
        minSdk = 23
        targetSdk = 36
        versionCode = appVersionCode
        versionName = "$appVersionName.build$buildTimestamp"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        multiDexEnabled = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

base {
    archivesName.set("$appProductName-$appVersionName.build$buildTimestamp")
}

dependencies {
    // Option 1: Minimum Setup for Basic Lottie Loading (without di)
    // implementation(libs.bundles.lottie.basic.app)

    // Option 2: Full setup with DI and networking
    // Core Android
    implementation(libs.bundles.core.android)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)

    // Compose BOM - must be defined before compose dependencies
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.stack)

    // Lottie Animation
    // Choose one as needed:
    implementation(libs.bundles.lottie.minimal)        // Only Lottie Basic
    // implementation(libs.bundles.lottie.remote)      // Lottie + remote loading
    // implementation(libs.bundles.lottie.network)     // Lottie + full network

    // Animation & UI
    implementation(libs.bundles.animation)

    // Navigation (If the app is complex)
    implementation(libs.bundles.navigation)

    // Network - Ktor Client (if necessary load from the internet)
    implementation(libs.bundles.network)

    // Dependency Injection - Koin (optional for simple loading)
    implementation(libs.bundles.di)

    // Image Loading
    implementation(libs.bundles.image.loading)

    // Utilities
    implementation(libs.timber)

    // Database (If you need to save preferences/cache)
    implementation(libs.bundles.database)
    ksp(libs.room.compiler)

    // Work Manager (for background loading)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koin.androidx.workmanager)

    // DataStore (for user preferences)
    implementation(libs.androidx.datastore.preferences)

    // Testing - Unit Tests
    testImplementation(libs.bundles.testing.unit)
    testImplementation(libs.bundles.testing.koin)
    testImplementation(libs.bundles.testing.network)

    // Testing - Android Instrumented Tests
    androidTestImplementation(libs.bundles.testing.android)
    androidTestImplementation(libs.bundles.testing.compose)

    // Debug builds only
    debugImplementation(libs.bundles.debug)
}