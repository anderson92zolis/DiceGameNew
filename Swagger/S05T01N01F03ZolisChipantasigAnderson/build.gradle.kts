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
	implementation("org.springframework.security:spring-security-config:6.0.2")
	implementation("org.springframework.security:spring-security-config:6.0.2")
	implementation("org.springframework.security:spring-security-config:6.0.2")

	testImplementation("junit:junit:4.13.1")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testImplementation("org.springframework.security:spring-security-test")



	implementation("io.jsonwebtoken:jjwt-api:0.11.5") //JWT LIBRARY
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5") //JWT LIBRARY
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5") //JWT LIBRARY

	// 5.1 dependencies

	//implementation("io.springfox:springfox-swagger2:3.0.0")
	//implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4") //OPEN API AMB SWAGGER PER SPRING MVC

	//swagger
	/*
	implementation("io.swagger.core.v3:swagger-annotations:2.2.8")

	implementation("io.springfox:springfox-swagger2:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4") //OPEN API AMB SWAGGER PER SPRING MVC

	 */



	// https://www.baeldung.com/mockito-junit-5-extension

	// JUnit 5 dependencies
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")


	//ModelMapper
	//https://mvnrepository.com/artifact/org.modelmapper/modelmapper/3.1.1
	implementation("org.modelmapper:modelmapper:3.1.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
