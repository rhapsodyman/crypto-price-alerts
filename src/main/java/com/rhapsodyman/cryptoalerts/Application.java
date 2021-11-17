package com.rhapsodyman.cryptoalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    // Swagger
        // http://localhost:9000/swagger-ui/index.html


    // Deploy to tomcat
    //	https://www.vojtechruzicka.com/spring-boot-war/
    //	https://www.baeldung.com/spring-boot-war-tomcat-deploy

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}