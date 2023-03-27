package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.entities.LastProbe;
import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.LastProbeRepository;
import telran.monitoring.service.AnalyzerServiceImpl;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AnalyzerServiceTest {
	
	private static final long PATIENT_NO_REDIS_DATA = 3;
	private static final long PATIENT_NO_JUMP = 1;
	private static final long PATIENT_JUMP = 2;
	@MockBean
	LastProbeRepository probesRepository;
	@Autowired
	AnalyzerServiceImpl analyzerService;
	LastProbe probeNoJump = new LastProbe(1, 70);
	LastProbe probeJump = new LastProbe(2, 120);
	
	PulseProbe pulseProbeNoJump = new PulseProbe (1, 0, 0, 80);
	PulseProbe pulseProbeJump = new PulseProbe (2, 0, 0, 200);
	
	PulseJump pulseJump = new PulseJump(2, 120, 200);
	
	
	@BeforeEach
	void mockingService() {
		when(probesRepository.findById(PATIENT_NO_REDIS_DATA)).thenReturn(Optional.ofNullable(null)); 
		when(probesRepository.findById(PATIENT_NO_JUMP)).thenReturn(Optional.of(probeNoJump)); 
		when(probesRepository.findById(PATIENT_JUMP)).thenReturn(Optional.of(probeJump));  
	}

	@Test
	void patientNoRedisDataTest() {
		PulseJump jump = analyzerService.processPulseProbe(new PulseProbe());
		assertNull(jump);
	}
	
	@Test
	void PatientNoJumpTest() {
		PulseJump jump = analyzerService.processPulseProbe(pulseProbeNoJump);
		assertNull(jump);
	}
	
	@Test
	void patientJumpTest() {
		PulseJump jump = analyzerService.processPulseProbe(pulseProbeJump);
		assertEquals(pulseJump, jump);	
	}

}
