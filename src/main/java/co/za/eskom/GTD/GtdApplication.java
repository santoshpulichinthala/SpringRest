package co.za.eskom.GTD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"co.za.eskom"})
@EnableJpaRepositories("co.za.eskom.repository")
@EntityScan("co.za.eskom.model")
public class GtdApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtdApplication.class, args);
	}
}
