package telran.monitoring.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;

@RestController
@RequestMapping("data")
public class DataProviderController {

	@Autowired
	DataProviderService service;
	private static Logger LOG = LoggerFactory.getLogger(DataProviderController.class);
	
	@GetMapping("/{patientId}")
	NotificationData getNotificationDataById(@PathVariable long patientId) {
		LOG.debug("*data-provider* request for getting notification data by patient id: {}", patientId);
		return service.getNotificationData(patientId);
	}
}
