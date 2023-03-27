package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

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

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AnalyzerService;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AnalyzerControllerTest {

	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	@MockBean
	AnalyzerService service;
	PulseProbe probeNoJump = new PulseProbe (1, 0, 0, 70);
	PulseProbe probeJump = new PulseProbe (2, 0, 0, 200);
	PulseJump pulseJump = new PulseJump(2, 0, 200);
	
	@BeforeEach
	void mockingService() {
		when(service.processPulseProbe(probeJump)).thenReturn(pulseJump);
		when(service.processPulseProbe(probeNoJump)).thenReturn(null);
	}
	
	@Test
	void test() {
		
	}
	
	@Test
	void receivingProbNoJump() {
		producer.send(new GenericMessage<PulseProbe>(probeNoJump));//, "pulseProbeConsumer-in-0"
		Message<byte[]> message = consumer.receive(10, "jumps-out-0");
		assertNull(message);
	}
	
	@Test
	void receivingProbJump() throws StreamReadException, DatabindException, IOException {
		producer.send(new GenericMessage<PulseProbe>(probeJump));//, "pulseProbeConsumer-in-0"
		Message<byte[]> message = consumer.receive(10, "jumps-out-0");
		assertNotNull(message);
		ObjectMapper mapper = new ObjectMapper();
		PulseJump jump = mapper.readValue(message.getPayload(), PulseJump.class);
		assertEquals(pulseJump, jump);	
		assertTrue(pulseJump.equals(jump));
		}


}
