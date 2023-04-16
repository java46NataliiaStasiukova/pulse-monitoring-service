package telran.monitoring.controller;


import java.time.LocalDateTime;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import telran.monitoring.service.AvgValuesService;

@RestController
@RequestMapping(value="/pulse/values")
@Validated
public class AvgValuesController {
	
	static Logger LOG = LoggerFactory.getLogger(AvgValuesController.class);
	@Autowired
	AvgValuesService service;
	@GetMapping("{id}")
	Integer getAvgValue(@PathVariable ("id") @Positive long patientId,
			@RequestParam (name = "from", required = false) 
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = ("format required: yyyy-mm-dd")) String fromDateTime,
			@RequestParam(name = "to", required=false) 
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = ("format required: yyyy-mm-dd")) String toDateTime) {
		if (fromDateTime == null && toDateTime == null) {
			LOG.debug("*avg-values* request for getting avg pulse value by patient id: {}", patientId);
			return service.getAvgPulseValue(patientId);
		}
		LOG.debug("*avg-values* request for getting avg pulse value by patient id: {}, from date: {}, to date: {}", 
				patientId, fromDateTime, toDateTime);
		LocalDateTime from = fromDateTime == null ? LocalDateTime.of(1000, 1, 1, 0, 0) : LocalDateTime.parse(fromDateTime + "T00:00");
		LocalDateTime to = toDateTime == null ? LocalDateTime.of(10000, 1, 1, 0, 0) : LocalDateTime.parse(toDateTime + "T00:00");
		
		return service.getAvgPulseValueDates(patientId, from, to);
		
		
	}
}
