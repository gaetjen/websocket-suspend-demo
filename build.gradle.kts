plugins {
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.0"
    id("org.jetbrains.kotlin.kapt") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.4.0"
    id("io.micronaut.aot") version "4.4.0"
}

version = "0.1"
group = "de.johannesgaetjen"

val kotlinVersion= project.properties["kotlinVersion"]
repositories {
    mavenCentral()
}

dependencies {

    implementation("io.micronaut:micronaut-jackson-databind")

    // Kotlin Core
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Coroutines
    implementation(platform("org.jetbrains.kotlinx:kotlinx-coroutines-bom:1.8.1"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")

    // Micronaut Core
    kapt("io.micronaut:micronaut-inject-java")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    // YAML Config Parsing
    runtimeOnly("org.yaml:snakeyaml")

    // Database
    implementation(platform("org.jetbrains.exposed:exposed-bom:0.52.0"))
    implementation("org.jetbrains.exposed:exposed-core")
    implementation("org.jetbrains.exposed:exposed-dao")
    implementation("org.jetbrains.exposed:exposed-jdbc")
    implementation("org.jetbrains.exposed:exposed-java-time")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.mysql:mysql-connector-j:8.4.0")

    // Validation
    kapt("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut.validation:micronaut-validation")

    // Security
    kapt("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")

    // Http Client
    implementation("io.micronaut:micronaut-http-client")

    // CLI
    implementation("info.picocli:picocli")
    implementation("io.micronaut.picocli:micronaut-picocli")

    // Serialization
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")

    // Documentation
    kapt("io.micronaut.openapi:micronaut-openapi")
    implementation("io.swagger.core.v3:swagger-annotations")

    // Tracing
    implementation("io.micronaut.tracing:micronaut-tracing-core")

    // Management
    implementation("io.micronaut:micronaut-management")

    // Metrics
    kapt("io.micronaut.micrometer:micronaut-micrometer-annotation")
    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("co.elastic.logging:logback-ecs-encoder:1.6.0")
    implementation("io.github.oshai:kotlin-logging-jvm:6.0.9")

    // Bug Reporting
    implementation(platform("io.sentry:sentry-bom:7.11.0"))
    implementation("io.sentry:sentry")
    implementation("io.sentry:sentry-logback")

    // OpenTelemetry
    implementation(platform("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom-alpha:1.30.0-alpha"))
    implementation("io.opentelemetry:opentelemetry-api")
    implementation("io.opentelemetry:opentelemetry-sdk")
    implementation("io.opentelemetry:opentelemetry-sdk-extension-autoconfigure")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    implementation("io.opentelemetry.instrumentation:opentelemetry-kafka-clients-2.6")
    runtimeOnly("io.opentelemetry.instrumentation:opentelemetry-logback-mdc-1.0")

    // Bouncy Castle
    implementation("org.bouncycastle:bcprov-jdk18on:1.78.1")

    // Kotest
    testImplementation(platform("io.kotest:kotest-bom:5.9.1"))
    testImplementation("io.kotest:kotest-runner-junit5-jvm")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest.extensions:kotest-extensions-wiremock:3.1.0")
    testImplementation("org.wiremock:wiremock:3.8.0")
    testImplementation("io.strikt:strikt-core:0.35.1")
    testImplementation("com.h2database:h2:2.2.224")
}


application {
    mainClass = "de.johannesgaetjen.ApplicationKt"
}

kotlin {
    jvmToolchain(17)
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.openapi.project.dir", projectDir)
    }
}

allOpen {
    annotations("jakarta.inject.Inject", "jakarta.inject.Singleton")
    preset("micronaut")
}


graalvmNative.toolchainDetection = false
micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("de.johannesgaetjen.*")
    }
    aot {
    // Please review carefully the optimizations enabled below
    // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}



