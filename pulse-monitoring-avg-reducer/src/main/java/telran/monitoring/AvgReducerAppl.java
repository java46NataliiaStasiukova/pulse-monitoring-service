package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

@SpringBootApplication
public class AvgReducerAppl {
	
	@Autowired
	AvgReducerService reducerService;
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.avg.binding.name}")//:average-out-0}")
	private String bindingName;
	

	public static void main(String[] args) {
		
		SpringApplication.run(AvgReducerAppl.class, args);

	}
	
	@Bean
	Consumer<PulseProbe> pulseProbConsumer(){
		
		return this::pulseProbeReducing;//method reference 
	}
	
	void pulseProbeReducing(PulseProbe pulseProbe) {
		Integer avgPulseValue = reducerService.reduce(pulseProbe);
		if(avgPulseValue != null) {
			PulseProbe newProbe = new PulseProbe(pulseProbe.patientId , System.currentTimeMillis(), 0, avgPulseValue);
			
			
			streamBridge.send(bindingName, newProbe);
		}
	}

}
