buildscript {
  val androidCompileSdkVersion by extra(30)
  val androidMinSdkVersion by extra(21)
  val androidTargetSdkVersion by extra(30)

  repositories {
    google()
    mavenCentral()
  }
}

plugins {
  kotlin("android") apply false
  id("com.android.application") apply false
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}

tasks.register<Delete>("clean") {
  delete(rootProject.buildDir)
}