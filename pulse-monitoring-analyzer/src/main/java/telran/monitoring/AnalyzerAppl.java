package telran.monitoring;

import java.util.function.Consumer;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import jakarta.annotation.PreDestroy;
import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AnalyzerService;

@SpringBootApplication
public class AnalyzerAppl {
	
	static Logger LOG = LoggerFactory.getLogger(AnalyzerAppl.class);
	@Autowired
	AnalyzerService analyzerService;
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.jumps.binding.name}")
	private String bindingName;
	
	public static void main(String[] args) {
		
		SpringApplication.run(AnalyzerAppl.class, args);

	}
	
	@Bean
	Consumer<PulseProbe> pulseProbConsumer(){
		
		return this::pulseProbeAnalyzing;//method reference 
	}
	
	void pulseProbeAnalyzing(PulseProbe pulseProbe) {
		PulseJump pulseJump = analyzerService.processPulseProbe(pulseProbe);
		if(pulseJump != null) {
			LOG.debug("*analyzer* received pulse jump: {}, for pulse probe: {}", pulseJump, pulseProbe);
			streamBridge.send(bindingName, pulseJump);
		} else {
			LOG.debug("*analyzer* no pulse jump for pulse probe: {}", pulseProbe);
		}
	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("Analizer - shutdown has been performed");
	}

}
