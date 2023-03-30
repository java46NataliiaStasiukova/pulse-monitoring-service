package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;

import telran.monitoring.repo.DoctorRepository;
import telran.monitoring.repo.PatientRepository;
import telran.monitoring.repo.VisitRepository;
import telran.monitoring.service.VisitsService;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class VisitsBackOfficeServiceTest {
	
	@Autowired
	VisitsService service;
	@MockBean
	VisitRepository visitRepository;
	@MockBean 
	DoctorRepository doctorRepository;
	@MockBean
	PatientRepository patientRepository;

	@Test
	void test() {
		
	}

}
