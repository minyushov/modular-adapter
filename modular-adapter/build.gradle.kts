import java.util.Properties

plugins {
  id("com.android.library")
  kotlin("android")
  `maven-publish`
  signing
}

val androidCompileSdkVersion: Int by rootProject.extra
val androidMinSdkVersion: Int by rootProject.extra
val androidTargetSdkVersion: Int by rootProject.extra

android {
  compileSdkVersion(androidCompileSdkVersion)

  defaultConfig {
    minSdkVersion(androidMinSdkVersion)
    targetSdkVersion(androidTargetSdkVersion)
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
  api("androidx.recyclerview:recyclerview:1.2.0-beta01")
  implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

val sourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")

  val android = project
    .extensions
    .getByType<com.android.build.gradle.LibraryExtension>()

  val sourceSet = android
    .sourceSets
    .findByName("main")
    ?: throw GradleException("Unable to find 'main' source set")

  from(sourceSet.java.srcDirs)
}

afterEvaluate {
  val properties = rootProject
    .file("local.properties")
    .takeIf { it.isFile }
    ?.let { file ->
      Properties()
        .apply { load(file.inputStream()) }
    }

  publishing {
    publications {
      create<MavenPublication>("maven") {
        groupId = "io.github.minyushov"
        artifactId = "modular-adapter"
        version = "2.2.0"

        from(components.findByName("release"))
        artifact(sourcesJar)

        pom {
          name.set(artifactId)
          description.set("Modular adapter for RecyclerView")
          url.set("https://github.com/minyushov/modular-adapter")
          developers {
            developer {
              id.set("minyushov")
              name.set("Semyon Minyushov")
              email.set("minyushov@gmail.com")
            }
          }
          licenses {
            license {
              name.set("Apache-2.0")
              url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
          }
          scm {
            connection.set("https://github.com/minyushov/modular-adapter.git")
            developerConnection.set("https://github.com/minyushov/modular-adapter.git")
            url.set("https://github.com/minyushov/modular-adapter/tree/master")
          }
        }
      }
    }
    repositories {
      maven {
        name = "sonatype"
        setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
        credentials {
          username = properties["SONATYPE_USER"]
          password = properties["SONATYPE_PASSWORD"]
        }
      }
    }
  }

  signing {
    useInMemoryPgpKeys(
      properties["GPG_KEY_ID"],
      properties["GPG_KEY"],
      properties["GPG_PASSWORD"]
    )
    sign(publishing.publications)
  }
}

operator fun Properties?.get(name: String): String? {
  var property = System.getenv(name)
  if (property.isNullOrEmpty()) {
    property = this?.getProperty(name)
  }
  return property
}