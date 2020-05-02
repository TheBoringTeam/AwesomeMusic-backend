import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.3.71"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.71"
}

group = "com.music"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    //Security
    implementation("org.springframework.security:spring-security-core")
    //Auth
    implementation("org.springframework.security:spring-security-jwt:1.1.0.RELEASE")
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.4.0.RELEASE")
    //Test
    implementation("org.springframework.boot:spring-boot-starter-test:2.1.6.RELEASE")
    //Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
//    implementation("org.springframework.security:spring-security-config")
    //Other TODO: Read articles about it, because probably it does a lot. java 9+ ?(what)
    implementation("javax.xml.bind:jaxb-api")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    //Tokens
    implementation("com.auth0:java-jwt:3.10.2")
    //Login attempts
    implementation("com.google.guava:guava:29.0-jre")
    //Mails
    implementation("org.springframework.boot:spring-boot-starter-mail")
    //Beautiful logs Log4j !11!
    implementation("log4j:log4j:1.2.17")
    //Some weird shit
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Database connection
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
