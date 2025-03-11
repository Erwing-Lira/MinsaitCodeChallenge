plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.minsaitcodechallenge"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.minsaitcodechallenge"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(project(":feature_map"))
    implementation(project(":feature_movie"))
    implementation(project(":feature_profile"))
    implementation(project(":feature_sync"))
    implementation(project(":feature_upload"))
    implementation(project(":network"))

    // Modules

    // Hilt
    implementation(libs.dagger.hilt)
    kapt(libs.kapt)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.kotlinx.serialization.json)
}

kapt {
    correctErrorTypes = true
}