plugins {
    id ("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.hub.wifianalysis"
    compileSdk =31

    defaultConfig {
        applicationId = "com.hub.wifianalysis"
        minSdk =19
        targetSdk =29
        versionCode =1
        versionName ="1.0"

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

    buildFeatures {
        dataBinding= true
    }

}

dependencies {

    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("com.google.android.material:material:1.5.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.activity:activity:1.2.3")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    implementation("com.github.tejmagar:AndroidNetworkTools:1.0.2alpha")
    implementation ("androidx.fragment:fragment-ktx:1.5.7")
    // navigation dependency
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")



}