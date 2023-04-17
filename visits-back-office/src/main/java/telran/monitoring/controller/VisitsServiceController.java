package telran.monitoring.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import telran.monitoring.service.VisitsService;
import telran.monitoring.dto.*;

@RestController
@RequestMapping(value="/visits")
@Validated
public class VisitsServiceController {
	
	@Autowired
	VisitsService visitsService;
	private static Logger LOG = LoggerFactory.getLogger(VisitsServiceController.class);
	
	@PostMapping("/patient")
	String addPatient(@RequestBody @Valid PatientDto patient) {
		LOG.info("*visits* request for adding patient: {}", patient.toString());
		visitsService.addPatient(patient);
		return String.format("patient %s was added", patient.toString());
	}
	
	@PostMapping("/doctor")
	String addDoctor(@RequestBody @Valid DoctorDto doctor) {
		LOG.info("*visits* request for adding patient: {}", doctor.toString());
		visitsService.addDoctor(doctor);
		return String.format("doctor %s was added", doctor.toString());
	}
	
	@PostMapping("/visit")
	String addVisit(@RequestBody @Valid VisitDto visit) {
		LOG.info("*visits* request for adding patient: {}", visit.toString());
		visitsService.addVisit(visit);
		return String.format("visit %s was added", visit.toString());
	}
	
	@GetMapping("/{patientId}")
	List<VisitDto> getVisitsData(@PathVariable @Positive long patientId,
			@RequestParam(name = "dateFrom", defaultValue = "", required = false) 
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "format required: yyyy-mm-dd") String from,
			@RequestParam(name = "dateTo", defaultValue = "", required = false) 
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "format required: yyyy-mm-dd")String to){
		if(from == null && to == null) {
			LOG.info("*visits* request for getting visits by patient id: {}", patientId);
			return visitsService.getAllVisits(patientId);
		}
		LOG.info("*visits* request for getting visits by patient id: {}, date from: {}, date to: {}", patientId, from, to);
		LocalDate fromDate = from == null ? LocalDate.of(1111, 1, 1) : LocalDate.parse(from);
		LocalDate toDate = to == null ? LocalDate.of(9999, 1, 1) : LocalDate.parse(to);
		
		return visitsService.getVisitsDates(patientId, fromDate, toDate);
	}

}
