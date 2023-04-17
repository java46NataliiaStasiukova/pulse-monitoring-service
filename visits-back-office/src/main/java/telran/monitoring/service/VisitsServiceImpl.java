package telran.monitoring.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.monitoring.entities.*;
import telran.monitoring.controller.VisitsServiceController;
import telran.monitoring.dto.*;
import telran.monitoring.repo.*;



@Service
public class VisitsServiceImpl implements VisitsService {
	private static Logger LOG = LoggerFactory.getLogger(VisitsServiceController.class);
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	DoctorRepository doctorRepository;
	@Autowired
	VisitRepository visitRepository;

	@Override
	public void addPatient(PatientDto patientDto) {
		Patient patient = patientRepository.findById(patientDto.getPatientId()).orElse(null);
		if(patient == null) {
			patientRepository.save(new Patient(patientDto.getPatientId(), patientDto.getPatientName()));
		} else {
			LOG.warn("*visits* patient: {} was not added to repository", patientDto.toString());
			throw new IllegalStateException(String.format("patient with id: %s already exists",
					patientDto.getPatientId()));
		}
		LOG.debug("*visits* patient: {} was added to repository", patientDto.toString());
	}

	@Override
	public void addDoctor(DoctorDto doctorDto) {
		Doctor doctor = doctorRepository.findById(doctorDto.getDoctorEmail()).orElse(null);
		if(doctor == null) {
			doctorRepository.save(new Doctor(doctorDto.getDoctorEmail(), doctorDto.getDoctorName()));
		} else {
			LOG.warn("*visits* doctor: {} was not added to repository", doctorDto.toString());
			throw new IllegalStateException(String.format("doctor with id: %s already exist",
					doctorDto.getDoctorEmail()));
		}
		LOG.debug("*visits* doctor: {} was added to repository", doctorDto.toString());
	}

	@Override
	public void addVisit(VisitDto visitDto) {
		Patient patient = patientRepository.findById(visitDto.getPatientId()).orElse(null);
		if(patient == null) {
			LOG.warn("*visits* patient with id: {} doesn't exist", visitDto.getPatientId());
			throw new IllegalStateException(String.format("patient with id: %s doesn't exist",
					visitDto.getPatientId()));
		}
		Doctor doctor = doctorRepository.findById(visitDto.getDoctorEmail()).orElse(null);
		if(doctor == null) {
			LOG.warn("*visits* doctor with id: {} doesn't exist", visitDto.getDoctorEmail());
			throw new IllegalStateException(String.format("doctor with id: %s doesn't exist", visitDto.getDoctorEmail()));
		}
		visitRepository.save(new Visit(doctor, patient, LocalDate.parse(visitDto.getDate())));
		LOG.debug("*visits* visit: {} was added to repository", visitDto.toString());
	}

	@Override
	public List<VisitDto> getAllVisits(long patientId) {
	
		return visitRepository.findByPatientId(patientId).stream().map(this::toVisitDto).toList();
	}
	
	private VisitDto toVisitDto(Visit visit) {
		
		return new VisitDto(visit.getPatient().getId(), visit.getDoctor().getEmail(), visit.getDate().toString());
	}

	@Override
	public List<VisitDto> getVisitsDates(long patientId, LocalDate from, LocalDate to) {
		
		return visitRepository.findByDateBetweenAndPatientId(from, to, patientId).stream().map(this::toVisitDto).toList();
	}

}
