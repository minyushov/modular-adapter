import java.util.Properties

plugins {
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.android.lib)
  @Suppress("DSL_SCOPE_VIOLATION")
  alias(libs.plugins.kotlin.android)
  `maven-publish`
  signing
}

android {
  namespace = "com.minyushov.adapter"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.minSdk.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_11.toString()
  }

  publishing {
    singleVariant("release") {
      withSourcesJar()
    }
  }
}

dependencies {
  api(libs.android.recyclerview)
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
        version = "2.3.0"

        from(components.findByName("release"))

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