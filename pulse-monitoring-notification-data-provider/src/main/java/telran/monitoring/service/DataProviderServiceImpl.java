package telran.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.model.NotificationData;
import telran.monitoring.repo.DoctorRepository;
import telran.monitoring.repo.PatientRepository;
import telran.monitoring.repo.VisitRepository;

@Service
@Transactional(readOnly = true)
public class DataProviderServiceImpl implements DataProviderService {

	private static Logger LOG = LoggerFactory.getLogger(DataProviderServiceImpl.class);
	DoctorRepository doctorRepository;
	PatientRepository patientRepository;
	VisitRepository visitRepository;
	
	public DataProviderServiceImpl(DoctorRepository doctorRepository, PatientRepository patientRepository,
			VisitRepository visitRepository) {
		super();
		this.doctorRepository = doctorRepository;
		this.patientRepository = patientRepository;
		this.visitRepository = visitRepository;
	}
	
	@Override
	public NotificationData getNotificationData(long patientId) {
		String doctorEmail = visitRepository.getDoctorEmail(patientId);
		String doctorName = doctorRepository.findById(doctorEmail).get().getName();
		String patientName = patientRepository.findById(patientId).get().getName();
		return new NotificationData(doctorEmail, doctorName, patientName);
	}

}
