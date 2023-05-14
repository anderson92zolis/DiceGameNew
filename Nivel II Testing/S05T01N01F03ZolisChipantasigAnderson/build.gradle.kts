plugins {
	java
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "cat.itacademy.barcelonactiva.zolischipantasig.anderson.s05.t02.n01.f03"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	testImplementation("junit:junit:4.13.1")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("org.springframework.security:spring-security-test")



	implementation("io.jsonwebtoken:jjwt-api:0.11.5") //JWT LIBRARY
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5") //JWT LIBRARY
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") //JWT LIBRARY

	// https://www.baeldung.com/mockito-junit-5-extension

	//testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
	//testImplementation("org.mockito:mockito-core:4.6.1")

	// JUnit 5 dependencies
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

	// Mockito dependencies
	testImplementation("org.mockito:mockito-core:4.6.1")
	//testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

	// AssertJ
	testImplementation("org.assertj:assertj-core:3.21.0")



	//ModelMapper
	//https://mvnrepository.com/artifact/org.modelmapper/modelmapper/3.1.1
	implementation("org.modelmapper:modelmapper:3.1.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
