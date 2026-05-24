plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
    alias(libs.plugins.kapt)
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    alias(libs.plugins.kotlin.parcelize)


}

android {
    namespace = "com.example.nowlocationn"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.nowlocationn"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
// Conversor GSON (para que Retrofit entienda el JSON)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //SerializedName
    implementation("com.google.code.gson:gson:2.10.1")
    // Retrofit: Cliente HTTP profesional
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // GSON Converter: Para convertir JSON a Objetos Kotlin automáticamente
    // Hilt (Ya deberías tenerlo, pero asegúrate de la versión)
    implementation("com.google.dagger:hilt-android:2.48")
    //Coil
    implementation("io.coil-kt:coil-compose:2.6.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    implementation(libs.hilt.android)
    //añadidas
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
}