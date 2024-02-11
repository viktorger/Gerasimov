plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.viktorger.tinkofffintechandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.viktorger.tinkofffintechandroid"
        minSdk = 24
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    // Dagger
    ksp(libs.dagger.compiler)
    implementation(libs.dagger)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Networking
    implementation(libs.retrofit2.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // Glide
    implementation(libs.github.glide)
    ksp(libs.glide.compiler)

    implementation(libs.shimmer)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}