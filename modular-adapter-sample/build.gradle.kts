plugins {
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.android.app)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.kotlin.android)
}

android {
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()

    versionCode = 1
    versionName = "1.0"
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }
}

dependencies {
  implementation(project(":modular-adapter"))
  implementation(libs.android.appcompat)
}