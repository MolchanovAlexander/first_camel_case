package com.example.first_camel_case;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {
		"classpath:camel/camel-context.xml",
		"classpath:camel/camel-context2.xml"
})
public class FirstCamelCaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstCamelCaseApplication.class, args);
	}

}
