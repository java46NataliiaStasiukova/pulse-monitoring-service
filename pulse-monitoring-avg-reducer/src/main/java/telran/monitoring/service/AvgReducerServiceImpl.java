package telran.monitoring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import telran.monitoring.entities.ProbesList;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.ProbesListRepository;

@Service
public class AvgReducerServiceImpl implements AvgReducerService {
	
	@Autowired
	ProbesListRepository probesListRepository;
	@Value("${app.reducing.size:20}")
	int reducingSize;
	static Logger LOG = LoggerFactory.getLogger(AvgReducerService.class);

	@Override
	@Transactional
	public Integer reduce(PulseProbe probe) {
		ProbesList probesList = probesListRepository.findById(probe.patientId)
				.orElse(null);
		Integer avgValue = null;
		if (probesList == null) {
			LOG.debug("for patient {} no saved pulse values", probe.patientId);
			probesList = new ProbesList(probe.patientId);
		} else {
			LOG.trace("for patient {} number of saved pulse values is {}",
					probesList.getPatientId(), probesList.getPulseValues().size());
		}
		List<Integer> pulseValues = probesList.getPulseValues();
		pulseValues.add(probe.value);
		if (pulseValues.size() >= reducingSize) {
			 avgValue = pulseValues.stream().collect(Collectors.averagingInt(x -> x)).intValue();
			pulseValues.clear();
		}
		probesListRepository.save(probesList);
		return avgValue;
		
	}
	
	@PostConstruct
	void inintDebugInfo() {
		LOG.debug("##reducing size is {}", reducingSize);
	}

}
