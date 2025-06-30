plugins {
    id("io.micronaut.application") version "4.5.4"
    id("com.gradleup.shadow") version "8.3.7"
}

group = "org.example"

repositories {
    mavenCentral()
}

tasks.processResources { // Access the task by its name
    filesMatching("application.properties") {
        expand(project.properties)
    }
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("info.picocli:picocli")
    implementation("io.micronaut.picocli:micronaut-picocli")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    runtimeOnly("ch.qos.logback:logback-classic")
}

java {
    sourceCompatibility = JavaVersion.toVersion("21")
    targetCompatibility = JavaVersion.toVersion("21")
}

application {
    mainClass = "org.example.DemoCommand"
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("org.example.*")
    }
}

