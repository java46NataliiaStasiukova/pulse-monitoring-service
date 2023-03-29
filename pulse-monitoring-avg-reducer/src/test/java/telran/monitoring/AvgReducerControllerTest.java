package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerControllerTest {

private static final long ID_NO_AVG = 123;
private static final long ID_AVG = 124;
private static final int VALUE = 80;
@Autowired
InputDestination producer;
@Autowired
OutputDestination consumer;
@MockBean
AvgReducerService service;
@Value("${app.avg.binding.name}")
private String bindingNameConsumer;
//String bindingNameProducer = "pulseProbeConsumer-in-0";//***

PulseProbe probeNoAvg = new PulseProbe (ID_NO_AVG, 0, 0, VALUE);
PulseProbe probeAvg = new PulseProbe (ID_AVG, 0, 0, VALUE);

	@BeforeEach
	void mockingService() {
		when(service.reduce(probeAvg)).thenReturn(probeAvg.value);
		when(service.reduce(probeNoAvg)).thenReturn(null);
	}
	
	@Test
	void receivingProbeAvg() throws Exception{
		producer.send(new GenericMessage<PulseProbe>(probeAvg));//***
		Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
		assertNotNull(message);
		
		ObjectMapper mapper = new ObjectMapper();
		PulseProbe probeAvgActual = mapper.readValue(message.getPayload(), PulseProbe.class);
		assertEquals(probeAvg, probeAvgActual);
	}
	
	@Test 
	void receivingProbeNoAvg(){
		producer.send(new GenericMessage<PulseProbe>(probeNoAvg));//***
		Message<byte[]> message = consumer.receive(10, "average-out-0");
		assertNull(message);
	}

}
