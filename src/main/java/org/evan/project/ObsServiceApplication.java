package org.evan.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.evan.project.repository")
public class ObsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObsServiceApplication.class, args);
	}

}
