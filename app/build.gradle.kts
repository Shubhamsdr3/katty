plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id ("com.google.dagger.hilt.android")
    id ("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.pandey.shubham.katty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pandey.shubham.katty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
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
        buildConfig = true
        dataBinding = true
    }

    flavorDimensions += "version"
    productFlavors  {
        create("dev") {
            buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/\"")
            buildConfigField("String", "CAT_API_KEY", "\"17d94b92-754f-46eb-99a0-65be65b5d18f\"")
        }

        create("prod") {
            buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/\"")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    // paging
    implementation("androidx.room:room-paging:2.6.1")

    //Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    ksp("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0")
    ksp("com.google.dagger:hilt-compiler:2.50")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    // chucker
    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")

    // glide
    implementation("com.github.bumptech.glide:glide:4.13.2")
    ksp("com.github.bumptech.glide:compiler:4.13.2")
    implementation("androidx.palette:palette-ktx:1.0.0")

    // lottie
    implementation("com.airbnb.android:lottie:6.3.0")

    // paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    testImplementation("androidx.paging:paging-common-ktx:3.2.1")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")
}