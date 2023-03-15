package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerControllerTest {

@Autowired
InputDestination producer;
@Autowired
OutputDestination consumer;
@MockBean
AvgReducerService service;

PulseProbe probeNoAvg = new PulseProbe ();
PulseProbe probeAvg = new PulseProbe (2, 0, 0, 120);

@BeforeEach
void mockingService() {
	when(service.reduce(probeAvg)).thenReturn(probeAvg.value);
	when(service.reduce(probeNoAvg)).thenReturn(null);
}
	
	@Test
	void test() {
		
	}
	
	@Test
	void receivingProbeAvg() throws Exception{
		producer.send(new GenericMessage<PulseProbe>(probeAvg));
		Message<byte[]> message = consumer.receive(10, "average-out-0");
		assertNotNull(message);
	}
	
	@Test 
	void receivingProbeNoAvg(){
		producer.send(new GenericMessage<PulseProbe>(probeNoAvg));
		Message<byte[]> message = consumer.receive(10, "average-out-0");
		assertNull(message);
	}

}
//ObjectMapper mapper = new ObjectMapper();
//PulseJump jump = mapper.readValue(message.getPayload(), PulseJump.class);
////assertEquals(pulseJump, jump);	
//assertTrue(pulseJump.equals(jump));
//}
