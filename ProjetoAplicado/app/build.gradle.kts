plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ipca.stock.projetoaplicado"
    compileSdk = 34

    defaultConfig {
        applicationId = "ipca.stock.projetoaplicado"
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // define a BOM and its version
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))

    // define any required OkHttp artifacts without version
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0")

    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    implementation ("com.jakewharton.threetenabp:threetenabp:1.3.1")
    implementation ("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0")
    implementation ("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")




}