package com.miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@SwaggerDefinition
@SpringBootApplication
public class MiniprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniprojectApplication.class, args);
	}

}
