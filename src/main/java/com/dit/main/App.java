package com.dit.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * @author anavulla
 *
 */
@ComponentScan("com.dit")
@EnableJpaRepositories("com.dit.repository")
@EntityScan("com.dit.model")
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/**
	 * @param appDesciption
	 * @param appVersion
	 * @return
	 */
	@Bean
	public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {

		return new OpenAPI().info(new Info().title("DIT Interview API").version(appVersion).description(appDesciption));

	}

}
