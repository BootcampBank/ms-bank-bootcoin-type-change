package com.bc48.bootcointypechange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

import java.util.logging.Logger;

@EnableEurekaClient
@SpringBootApplication
public class BootcoinApplication implements CommandLineRunner {

	private static final Logger logger = Logger.getLogger(BootcoinApplication.class.toString());

	@Autowired
	private Environment env;

	@Override
	public void run(String... args) throws Exception {

		logger.info("Application name: " + env.getProperty("spring.application.name"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BootcoinApplication.class, args);
	}

}
