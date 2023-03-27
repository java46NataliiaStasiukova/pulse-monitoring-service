package telran.monitoring.controller;


import java.time.LocalDateTime;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.monitoring.service.AvgValuesService;

@RestController
@RequestMapping("data")
public class AvgValuesController {

	static Logger LOG = LoggerFactory.getLogger(AvgValuesController.class);
	@Autowired
	AvgValuesService valuesService;
	
	@GetMapping
	int getAvgValue(@RequestParam (name = "patientId")long patientId, 
			@RequestParam(name = "dateFrom", defaultValue = "")LocalDateTime from,
			@RequestParam(name = "dateTo", defaultValue = "")LocalDateTime to) {
		LOG.debug("request for get avg value with patientId: {}, date from: {},"
				+ "date to: {}", patientId, from, to);
		return from == null || to == null ? valuesService.getAvgValue(patientId) : 
			valuesService.getAvgValue(patientId, from, to);
	}
	
}
