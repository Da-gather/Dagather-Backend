package kr.org.dagather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DagatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(DagatherApplication.class, args);
	}

}
