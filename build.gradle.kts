plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}