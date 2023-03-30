package telran.monitoring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.dto.*;
import telran.monitoring.service.VisitsService;


@SpringBootTest
@AutoConfigureMockMvc
class VisitsBackOfficeControllerTest {
	
	@MockBean
	VisitsService service;
	@Autowired
	MockMvc mockMvc;
	ObjectMapper mapper = new ObjectMapper();

	@Test
	@Order(1)
	@Sql(scripts = {"DoctorsPatientsVisits.sql"})
	void getVisitsByPatientIdTest() throws Exception {
		mockMvc.perform(get("/data/visits?patientId=124"))
				.andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}
	
	@Test
	@Order(2)
	void getVisitsByPatientIdFromToDateTest() throws Exception {
		mockMvc.perform(get("/data/visits?patientId=123&from=2023-03-02&to=2023-03-16"))
				.andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
	}
	
	@Test
	@Order(3)
	void addPatientTest() throws Exception {
		PatientDto patient = new PatientDto(125, "Kolya");
		String patientJSON = mapper.writeValueAsString(patient);
		mockMvc.perform(post("http://localhost/data/patients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(patientJSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(4)
	void addDoctorTest() throws Exception {
		DoctorDto doctor = new DoctorDto("doctor4@gmail.com", "doctor4");
		String doctorJSON = mapper.writeValueAsString(doctor);
		mockMvc.perform(post("http://localhost/data/doctors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(doctorJSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(5)
	void addVisitTest() throws Exception {
		VisitDto visit = new VisitDto(125, "doctor4@gmail.com", "2023-03-01");
		String visitJSON = mapper.writeValueAsString(visit);
		mockMvc.perform(post("http://localhost/data/visits")
				.contentType(MediaType.APPLICATION_JSON)
				.content(visitJSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

}
