plugins {
  id("com.android.application")
  kotlin("android")
}

val androidCompileSdkVersion: Int by rootProject.extra
val androidMinSdkVersion: Int by rootProject.extra
val androidTargetSdkVersion: Int by rootProject.extra

android {
  compileSdkVersion(androidCompileSdkVersion)

  defaultConfig {
    minSdkVersion(androidMinSdkVersion)
    targetSdkVersion(androidTargetSdkVersion)
    setVersionCode(1)
    setVersionName("1.0")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}

dependencies {
  implementation(project(":modular-adapter"))
  implementation("org.jetbrains.kotlin:kotlin-stdlib")
  implementation("androidx.appcompat:appcompat:1.2.0")
}