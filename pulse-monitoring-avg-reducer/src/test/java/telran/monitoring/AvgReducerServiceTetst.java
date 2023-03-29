package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.entities.ProbesList;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.ProbesListRepository;
import telran.monitoring.service.AvgReducerService;



@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerServiceTetst {
	
	@MockBean
	ProbesListRepository probesRepository;
	@Autowired
	AvgReducerService service;
	
	private static final long ID_NO_REDIS_DATA = 1;
	private static final long ID_AVG = 123;
	private static final long ID_NO_AVG = 124;
	private static final int VALUE = 80;
	static ProbesList probeNoAvg = new ProbesList(ID_NO_AVG);
	static ProbesList probeAvg = new ProbesList(ID_AVG);
	static List<Integer> emptyProbeList;
	static List<Integer> oneProbeList;
	

	@BeforeAll
	static void setUp() {
		emptyProbeList = probeNoAvg.getPulseValues();
		oneProbeList = probeAvg.getPulseValues();
		oneProbeList.add(VALUE);
	}
	
	@BeforeEach
	void mockingService() {
		when(probesRepository.findById(ID_NO_REDIS_DATA)).thenReturn(Optional.ofNullable(new ProbesList()));
		when(probesRepository.findById(ID_NO_AVG)).thenReturn(Optional.of(probeNoAvg));
		when(probesRepository.findById(ID_AVG)).thenReturn(Optional.of(probeAvg));
	}
	
	@Test
	void avgValueNoRedisData() {
		Integer avgProbes = service.reduce(new PulseProbe(ID_NO_REDIS_DATA, 0, 0, VALUE));
		assertNull(avgProbes);
	}
	
	@Test
	void noAvgValueTest() {
		Integer pulseAvg = service.reduce(new PulseProbe(ID_NO_AVG, 0, 0, VALUE));
		assertNull(pulseAvg);
		assertEquals(1, emptyProbeList.size());
	}

	@Test
	void avgValueTest() {
		Integer pulseAvg = service.reduce(new PulseProbe(ID_AVG, 0, 0, VALUE));
		assertEquals(VALUE, pulseAvg);
		assertEquals(0, oneProbeList.size());
	}


}
