package telran.monitoring.controller;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.monitoring.model.NotificationData;
import telran.monitoring.service.DataProviderService;

@RestController
@RequestMapping("college")
public class DataProviderController {

	@Autowired
	DataProviderService service;
	private static Logger LOG = LoggerFactory.getLogger(DataProviderController.class);
	
	@GetMapping("lastVisit")
	NotificationData getNotificationDataById(@RequestParam (name="patientId") long id) {
		LOG.debug("request for get notification data by id: {}", id);
		LOG.info("notification data: {}", service.getNotificationData(id).toString());
		//return service.getNotificationData(id);
		return null;
	}
}
