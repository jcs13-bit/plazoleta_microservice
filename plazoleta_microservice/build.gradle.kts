plugins {
	java
	id("org.springframework.boot") version "2.7.18"
	id("io.spring.dependency-management") version "1.1.4"
}
group = "com.pragma"
version = "0.0.1-SNAPSHOT"

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
	implementation ("org.mapstruct:mapstruct:1.5.5.Final")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation ("javax.validation:validation-api:2.0.1.Final")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("io.swagger.core.v3:swagger-annotations:2.2.20")
	implementation ("org.springdoc:springdoc-openapi-ui:1.8.0")
	implementation ("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation ("org.springframework.boot:spring-boot-starter-security")

	implementation ("io.jsonwebtoken:jjwt-api:0.12.5")

	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.5")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testAnnotationProcessor ("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.mockito:mockito-core:5.11.0")
	testImplementation ("org.junit.jupiter:junit-jupiter-api:5.10.0")
	testImplementation ("org.mockito:mockito-junit-jupiter:5.11.0")
	testImplementation ("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom ("org.springframework.cloud:spring-cloud-dependencies:2021.0.8")
	}
}



tasks.withType<Test> {
	useJUnitPlatform()
}
