package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class VisitsServiceAppl {

	public static void main(String[] args) {
		SpringApplication.run(VisitsServiceAppl.class, args);

	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("bye - shutdown has been performed");
	}

}
