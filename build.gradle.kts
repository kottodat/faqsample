// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenCentral()
        maven {  url = uri("https://maven.google.com") }
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.4.1")
        classpath(kotlin("gradle-plugin", version = "1.3.31"))
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.31")
        classpath("com.google.gms:google-services:4.2.0")
        classpath("com.google.android.gms:strict-version-matcher-plugin:1.1.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module guild.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}
tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}
