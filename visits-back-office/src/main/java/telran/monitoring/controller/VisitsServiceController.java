package telran.monitoring.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import telran.monitoring.service.VisitsService;
import telran.monitoring.dto.*;

@RestController
@RequestMapping("data")
public class VisitsServiceController {
	
	@Autowired
	VisitsService visitsService;
	private static Logger LOG = LoggerFactory.getLogger(VisitsServiceController.class);
	
	@PostMapping("/patients")
	String addPatient(@RequestBody @Valid PatientDto patient) {
		visitsService.addPatient(patient);
		return String.format("patient %s was added", patient.toString());
	}
	
	@PostMapping("/doctors")
	String addDoctor(@RequestBody @Valid DoctorDto doctor) {
		visitsService.addDoctor(doctor);
		return String.format("doctor was added", doctor.toString());
	}
	
	@PostMapping("/visits")
	String addVisit(@RequestBody @Valid VisitDto visit) {
		visitsService.addVisit(visit);
		return String.format("visit %s was added", visit.toString());
	}
	
	@GetMapping("/visits")
	List<VisitDto> getVisitsData(@RequestParam(name = "patientId") long patientId,
			@RequestParam(name = "dateFrom", defaultValue = "", required = false) LocalDate from,
			@RequestParam(name = "dateTo", defaultValue = "", required = false) LocalDate to){
		
		return from == null || to == null ? visitsService.getAllVisits(patientId) : 
			visitsService.getVisitsDates(patientId, from, to);
	}
	

}
