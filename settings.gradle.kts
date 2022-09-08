pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
  }

  versionCatalogs {
    create("libs") {
      version("agp", "7.4.0-alpha10")
      plugin("android-app", "com.android.application").versionRef("agp")
      plugin("android-lib", "com.android.library").versionRef("agp")

      version("kotlin", "1.7.10")
      plugin("kotlin-android", "org.jetbrains.kotlin.android").versionRef("kotlin")

      version("android-buildTools", "33.0.0")
      version("android-minSdk", "21")
      version("android-compileSdk", "33")
      version("android-targetSdk", "33")

      library("android-appcompat", "androidx.appcompat:appcompat:1.5.0")
      library("android-recyclerview", "androidx.recyclerview:recyclerview:1.3.0-beta02")
    }
  }
}

include("modular-adapter")
include("modular-adapter-sample")