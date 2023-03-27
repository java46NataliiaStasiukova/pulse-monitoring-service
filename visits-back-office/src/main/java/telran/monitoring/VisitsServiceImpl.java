package telran.monitoring;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.monitoring.entities.*;
import telran.monitoring.model.*;
import telran.monitoring.repo.*;

@Service
public class VisitsServiceImpl implements VisitsService {
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	VisitRepository visitRepository;

	@Override
	public void addPatient(PatientDto patientDto) {
		
		patientRepository.save(new Patient(patientDto.patientId, patientDto.patientName));
	}

	@Override
	public void addDoctor(DoctorDto doctorDto) {
		
		doctorRepository.save(new Doctor(doctorDto.doctorEmail, doctorDto.doctorName));
	}

	@Override
	public void addVisit(VisitDto visitDto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
		Patient patient = patientRepository.findById(visitDto.patientId).orElse(null);
		Doctor doctor = doctorRepository.findById(visitDto.doctorEmail).orElse(null);
		visitRepository.save(new Visit(doctor, patient, 
				LocalDate.parse("2005-nov-12", formatter)));
	}

	@Override
	public List<VisitDto> getAllVisits(long patientId) {
		
		return null;
		//return visitRepository.findById(patientId).stream().toList();
	}

	@Override
	public List<VisitDto> getVisitsDates(long patientId, LocalDate from, LocalDate to) {
		// TODO Auto-generated method stub
		return null;
	}

}
