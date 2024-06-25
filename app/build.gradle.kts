plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.yesnightmarket"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.yesnightmarket"
        minSdk = 29
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("com.google.accompanist:accompanist-insets:0.27.0")
    //compose
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.compose.ui:ui:1.6.7")
    implementation ("androidx.compose.material:material:1.6.7")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.6.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation ("androidx.activity:activity-compose:1.4.0")
    //viewmodel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation ("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.7")
    //google location
    implementation("com.google.android.gms:play-services-location:21.1.0")
    implementation("com.google.accompanist:accompanist-permissions:0.35.0-alpha")
    //google map
    implementation ("com.google.maps.android:maps-compose:2.11.4")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.0.1")
    // Moshi
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    // OKHttp Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    //繪圖用
    implementation ("io.coil-kt:coil-compose:2.5.0")
    implementation ("io.getstream:sketchbook:1.0.4")
    //分頁
  //  implementation(libs.androidx.navigation.compose)
    //儲存用
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation(libs.androidx.navigation.compose)
//    implementation(libs.firebase.auth)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.androidx.work.runtime.ktx)
    //camera
    val cameraxVersion = "1.3.3"
    implementation ("androidx.camera:camera-core:${cameraxVersion}")
    implementation ("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation ("androidx.camera:camera-view:${cameraxVersion}")
    implementation ("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation(kotlin("script-runtime"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}