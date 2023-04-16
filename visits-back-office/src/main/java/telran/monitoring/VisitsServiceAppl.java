package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan(basePackages= {"telran"})
public class VisitsServiceAppl {

	public static void main(String[] args) {
		SpringApplication.run(VisitsServiceAppl.class, args);

	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("VisitsService - shutdown has been performed");
	}

}
