pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
  plugins {
    kotlin("android") version "1.4.30"
  }
  resolutionStrategy {
    eachPlugin {
      val id = requested.id.id
      if (id == "com.android.application" || id == "com.android.library") {
        useModule("com.android.tools.build:gradle:7.0.0-alpha04")
      }
    }
  }
}

include("modular-adapter")
include("modular-adapter-sample")