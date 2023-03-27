package telran.monitoring.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvgValuesServiceImpl implements AvgValuesService {
	
	

	@Override
	@Transactional(readOnly = true)
	public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public int getAvgValue(long patientId) {
		
		return 0;
	}

}
