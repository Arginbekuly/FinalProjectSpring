package finalProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication

public class TheoryConspiracyValidatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheoryConspiracyValidatorApplication.class, args);
	}

}
