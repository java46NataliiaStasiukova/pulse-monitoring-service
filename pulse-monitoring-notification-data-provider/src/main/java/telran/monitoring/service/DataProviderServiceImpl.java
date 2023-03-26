package telran.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
		LOG.debug("id: {}", patientId);
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		LOG.debug("doctor email: {}", doctorEmail);
		String doctorName = doctorRepository.findById(doctorEmail).get().getName();
		LOG.debug("doctorName: {}", doctorName);
		String patientName = patientRepository.findById(patientId).get().getName();
		LOG.debug("patientName: {}", patientName);
		return new NotificationData(doctorEmail, doctorName, patientName);
	}

}
