package com.sample.WireMockSpringIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class WireMockSpringIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(WireMockSpringIntegrationApplication.class, args);
	}

}
