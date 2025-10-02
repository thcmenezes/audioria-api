package com.github.thcmenezes.audioria_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AudioriaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudioriaApiApplication.class, args);
	}

}
