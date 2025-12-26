package net.datasa.ex_javaexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ExJavaexamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExJavaexamApplication.class, args);
	}

}
