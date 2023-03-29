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

import telran.monitoring.service.AvgValuesService;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgValuesControllerTest {
	
	private static final long PATIENT_ID = 123;
	private static final int AVG_VALUE = 80;
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	@MockBean
	AvgValuesService service;
	
	@BeforeEach
	void mockingService() {
		when(service.getAvgValue(PATIENT_ID)).thenReturn(AVG_VALUE);
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
