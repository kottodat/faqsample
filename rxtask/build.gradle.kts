
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
//    id("com.google.gms.google-services")
}
android {
    compileSdkVersion(28)
    defaultConfig {
        //        applicationId = "com.kottodat.gsfirebase"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.31")
    implementation("androidx.appcompat:appcompat:1.1.0-alpha02")
    implementation("androidx.core:core-ktx:1.1.0-alpha04")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.2-alpha01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.2-alpha01")


    implementation(platform("com.google.firebase:firebase-bom:17.0.0"))
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    api("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

}//implementation(Config.Libs.Firebase.core)


apply(plugin = "com.google.android.gms.strict-version-matcher-plugin")
