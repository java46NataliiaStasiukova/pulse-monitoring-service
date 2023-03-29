package telran.monitoring.service;

import java.time.LocalDateTime;
import java.util.*;


import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.entities.AvgPulseDoc;
import telran.monitoring.repo.AvgValuesRepository;

@Service
public class AvgValuesServiceImpl implements AvgValuesService {
	
	private static Logger LOG = LoggerFactory.getLogger(AvgValuesServiceImpl.class);
//	@Autowired
//	AvgValuesRepository avgValuesRepository;
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	@Transactional(readOnly = true)
	public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
		
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public int getAvgValue(Long patientId) {
		MatchOperation matchOperation = match(Criteria.where("patientId").is(patientId));
		
		//avgValuesRepository.findAllById(patientId);
		//LOG.info("**pulseDocList: {}", pulseDocList.toString());
		
		return 0;
	}

}
