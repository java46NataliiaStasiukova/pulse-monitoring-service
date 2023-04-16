package telran.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import telran.exceptions.NotFoundException;

//import telran.exceptions.NotFoundException;

import telran.monitoring.entities.Doctor;
import telran.monitoring.entities.Patient;
import telran.monitoring.model.NotificationData;
import telran.monitoring.repo.DoctorRepository;
import telran.monitoring.repo.PatientRepository;
import telran.monitoring.repo.VisitRepository;

@Service
public class DataProviderServiceImpl implements DataProviderService {

	private static Logger LOG = LoggerFactory.getLogger(DataProviderServiceImpl.class);
	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;
	
	public DataProviderServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}
	
	@Override
	public NotificationData getNotificationData(long patientId) {
//		LOG.debug("*data-provider* get notification data by patient id: {}", patientId);
//		String doctorEmail = visitRepository.getDoctorEmail(patientId);
//		LOG.debug("*data-provider* doctor email: {}", doctorEmail);
//		if(doctorEmail == null || doctorEmail.isEmpty()) {
//			LOG.warn("*datat-provider* no doctor email for patient with id: {]", patientId);
//			throw new NotFoundException("no visits found for patient id: %s" + patientId);
//		}
//		String doctorName = doctorRepository.findById(doctorEmail).get().getName();
//		LOG.debug("*data-provider* doctor name: {}", doctorName);
//		if(doctorName == null) {
//			LOG.warn("*datat-provider* no doctor name for patient with id: {]", patientId);
//			throw new NotFoundException("no doctor found with id: %s" + doctorEmail);
//		}
//		String patientName = patientRepository.findById(patientId).get().getName();
//		LOG.debug("*data-provider* patient name: {}", patientName);
//		if(patientName == null) {
//			LOG.warn("*datat-provider* no patient name for patient with id: {]", patientId);
//			throw new NotFoundException("no patient found with id: %s" + patientId);
//		}
//		NotificationData notificationData = new NotificationData(doctorEmail, doctorName, patientName);
//		LOG.debug("*data-provider* getting notification data: {} by patient id: {}", notificationData, patientId);
//		return notificationData;
		
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		LOG.debug("doctor email is {}", doctorEmail);
		if(doctorEmail == null || doctorEmail.isEmpty()) {
			throw new NotFoundException("no vists for patient " + patientId);
		}
		Doctor doctor = doctorRepository.findById(doctorEmail).orElse(null);
		if(doctor == null) {
			throw new NotFoundException("no doctor with email " + doctorEmail);
		}
		String doctorName = doctor.getName();
		Patient patient = patientRepository.findById(patientId).orElse(null);
		if(patient == null) {
			throw new NotFoundException("no  patient " + patientId);
		}
		String patientName = patient.getName();
		return new NotificationData(doctorEmail, doctorName, patientName);
	}

}
