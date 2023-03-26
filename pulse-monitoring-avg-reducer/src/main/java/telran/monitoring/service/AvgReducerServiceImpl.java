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
		Integer res = null;
		ProbesList probesList = probesListRepository.findById(probe.patientId).orElse(null);
		if(probesList != null) {
			probesList = new ProbesList(probe.patientId);
		} else {
			//log
		}
		List<Integer> pulseValues = probesList.getPulseValues();
		pulseValues.add(probe.value);
		if(pulseValues.size() >= reducingSize) {
			res = pulseValues.stream().collect(Collectors.averagingInt(x -> x)).intValue();
			pulseValues.clear();
		}
		probesListRepository.save(probesList);
		return res;
	}
	
	@PostConstruct
	void inintDebugInfo() {
		LOG.debug("reducing size is {}", reducingSize);
	}

}
