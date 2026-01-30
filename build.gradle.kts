plugins {
	java
	id("io.quarkus") version "3.15.1"
}

group = "id.co.evan.project"
version = "0.0.1-SNAPSHOT"
description = "project"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.15.1"))
	implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
	implementation("io.quarkus:quarkus-hibernate-orm-panache")
	implementation("io.quarkus:quarkus-hibernate-validator")
	implementation("io.quarkus:quarkus-jdbc-h2")

	compileOnly("org.projectlombok:lombok:1.18.34")
	annotationProcessor("org.projectlombok:lombok:1.18.34")

	testImplementation("io.quarkus:quarkus-junit5")
	testImplementation("io.rest-assured:rest-assured")
}

tasks.withType<Test> {
	systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
	options.compilerArgs.add("-parameters")
}
