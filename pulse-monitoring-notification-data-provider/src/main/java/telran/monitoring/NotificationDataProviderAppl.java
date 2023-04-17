package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
@ComponentScan(basePackages= {"telran"})
public class NotificationDataProviderAppl {

	public static void main(String[] args) {
		SpringApplication.run(NotificationDataProviderAppl.class, args);

	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("DataProvider - shutdown has been performed");
	}

}
