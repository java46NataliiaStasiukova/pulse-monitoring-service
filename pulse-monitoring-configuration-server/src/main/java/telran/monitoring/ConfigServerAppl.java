package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

import jakarta.annotation.PreDestroy;



@SpringBootApplication
@EnableConfigServer
public class ConfigServerAppl {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerAppl.class, args);

	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("ConfigServer - shutdown has been performed");
	}

}
