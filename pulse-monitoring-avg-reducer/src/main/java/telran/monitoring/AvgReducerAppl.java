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
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

@SpringBootApplication
public class AvgReducerAppl {
	
	static Logger LOG = LoggerFactory.getLogger(AvgReducerAppl.class);
	@Autowired
	AvgReducerService reducerService;
	@Autowired
	StreamBridge streamBridge;
	@Value("${app.avg.binding.name}")
	private String bindingName;
	

	public static void main(String[] args) {
		
		SpringApplication.run(AvgReducerAppl.class, args);

	}
	
	@Bean
	Consumer<PulseProbe> pulseProbeConsumer(){
		
		return this::pulseProbeReducing;//method reference 
	}
	
	void pulseProbeReducing(PulseProbe pulseProbe) {
		Integer avgPulseValue = reducerService.reduce(pulseProbe);
		LOG.debug("*avg-reducer* received avg pulse value: {} by given pulse probe: {}", avgPulseValue, pulseProbe.toString());
		if(avgPulseValue != null) {
			PulseProbe newProbe = new PulseProbe(pulseProbe.patientId , System.currentTimeMillis(), 0, avgPulseValue);
			streamBridge.send(bindingName, newProbe);
		} else {
			LOG.warn("*avg-reduver* no avg pulse value for pulse probe: {}", pulseProbe.toString());
		}
	}
	
	@PreDestroy
	void preDestroy() {
		System.out.println("AvgReduser - shutdown has been performed");
	}

}
