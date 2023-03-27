package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class AvgValuesAppl {

	public static void main(String[] args) {
		SpringApplication.run(AvgValuesAppl.class, args);

	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("bye - shutdown has been performed");
	}

}