import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.gms.google-services")
}
android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.kottodat.faqsample"
        minSdkVersion(19)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    dataBinding.isEnabled = true
    lintOptions {
        isAbortOnError = false
}
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.31")
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.core:core-ktx:1.0.2")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.preference:preference:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.annotation:annotation:1.1.0-rc01")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.preference:preference:1.1.0-alpha05")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")

    implementation(platform("com.google.firebase:firebase-bom:17.0.0"))
    implementation("com.google.firebase:firebase-core")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    implementation("com.google.dagger:dagger-android:2.21")
    implementation("com.google.dagger:dagger-android-support:2.21")
    kapt("com.google.dagger:dagger-android-processor:2.21")
    /* Dagger2 - default dependency */
    kapt("com.google.dagger:dagger-compiler:2.21")

    api("io.reactivex.rxjava2:rxjava:2.2.6")
    implementation("io.reactivex.rxjava2:rxkotlin:2.3.0")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // anko
    implementation("org.jetbrains.anko:anko:0.10.8")

//    api project(path: ":gsfirebase")

    implementation(project(":gsfirebase"))
    implementation(project(":rxtask"))
    implementation(project(":gms"))
    implementation("com.jakewharton.timber:timber:4.7.1")

}
//plugins {
//    id("com.google.gms.google-services")
//}