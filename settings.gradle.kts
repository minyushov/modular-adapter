pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
  }
  plugins {
    kotlin("android") version "1.4.30"
    id("com.android.application") version "7.0.0-alpha04"
    id("com.android.library") version "7.0.0-alpha04"
  }
}

include("modular-adapter")
include("modular-adapter-sample")