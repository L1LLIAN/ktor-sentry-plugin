import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    `java-library`
    kotlin("jvm") version "1.7.10"
}

group = "lol.lily"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    api("io.sentry:sentry:6.1.4")

    val ktorVer = "2.0.3"
    implementation("io.ktor:ktor-server-core-jvm:$ktorVer")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVer")

    testImplementation("org.jetbrains.kotlin:kotlin-test:1.7.10")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withJavadocJar()
    withSourcesJar()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.7"
    }
}

publishing {
    repositories {
        maven {
            name = "sapphire-repository"

            val urlSuffix = if (version.toString().endsWith("SNAPSHOT")) "snapshots" else "releases"
            url = uri("https://maven.saph.llc/$urlSuffix")

            credentials {
                username = System.getenv("MAVEN_USER") ?: property("mavenUser").toString()
                password = System.getenv("MAVEN_PASS") ?: property("mavenPassword").toString()
            }
        }
    }

    publications {
        create<MavenPublication>("main") {
            from(components.getByName("java"))
            artifactId = "ktor-sentry-plugin"

            pom.withXml {
                val repositories = asNode().appendNode("repositories")
                project.repositories.findAll(closureOf<Any> {
                    if ((this is MavenArtifactRepository) && url.toString().startsWith("https")) {
                        val repository = repositories.appendNode("repository")
                        repository.appendNode("id", name)
                        repository.appendNode("url", url.toString())
                    }
                })
            }
        }
    }
}