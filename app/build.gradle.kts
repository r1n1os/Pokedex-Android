plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.pokedexandroid"
    compileSdk = 36

    buildToolsVersion = "36.0.0"
    ndkVersion = "28.1.13356709"

    defaultConfig {
        applicationId = "com.example.pokedexandroid"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
    buildFeatures {
        compose = true
    }
    composeCompiler {
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /**
     * Basic
     * */
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    /**
     * Compose
     * */
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    /**
     * Navigation
     * */
    implementation(libs.fragment.navigation.ktx)
    /**
     * Retrofit
     * */
    implementation(libs.retrofit)
    implementation(libs.gson)
    /**
     * Room Local Database
     * */
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    /**
     * Hilt
     * */
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.agp)
    implementation(libs.hilt.navigation.compose)
    /**
     * Coroutines
     * */
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    /**
     * Navigation
     * */
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    /**
     * Coil
     * */
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    /**
     * Testing
     * */
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}