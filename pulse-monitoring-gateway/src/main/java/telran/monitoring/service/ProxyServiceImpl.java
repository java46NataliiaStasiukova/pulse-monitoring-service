package telran.monitoring.service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.mvc.ProxyExchange;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProxyServiceImpl implements ProxyService {
	
	static Logger LOG = LoggerFactory.getLogger(ProxyService.class);
	@Value("${app.routed.urls}")
	List<String> urls;
	Map<String, String> mapOfUrls;//key - first item of URN, value - routed service URL

	@Override
	public ResponseEntity<byte[]> proxyRouting(ProxyExchange<byte[]> proxy,
			HttpServletRequest request, HttpMethod method) {
		String name = method.name();
		return switch(name) {
		case "GET" -> getRouting(proxy, request);
		case "POST" -> postRouting(proxy, request);
		default -> ResponseEntity.status(404)
		.body(String.format("Given Proxi implementation doesn't support HTTP method %s", name)
				.getBytes());
		};
	}
	
	private ResponseEntity<byte[]> getRouting(ProxyExchange<byte[]> proxy, 
			HttpServletRequest request) {
		String receivedURI = request.getRequestURI();
		LOG.debug("*gate way* received GET method with URI {}", receivedURI);
		String serviceURL = getServiceUrl(receivedURI);
		ResponseEntity<byte[]> resultEntity = checkServiceURL(serviceURL, receivedURI);
		if(resultEntity == null) {
			String queryString = request.getQueryString();
			String routedURI = String.format("%s%s?%s", serviceURL, receivedURI, 
					queryString != null ? queryString : "");
			LOG.debug("*gate way* routedURI is {}", routedURI);
			resultEntity = proxy.uri(routedURI).get();	
		}
		return resultEntity;
	}

	private ResponseEntity<byte[]> postRouting(ProxyExchange<byte[]> proxy, 
			HttpServletRequest request) {
		String receivedURI = request.getRequestURI();
		LOG.debug("*gate way* received POST method with URI {}", receivedURI);
		String serviceURL = getServiceUrl(receivedURI);
		ResponseEntity<byte[]> resultEntity = checkServiceURL(serviceURL, receivedURI);
		if(resultEntity == null) {
			String routedURI = serviceURL + receivedURI;
			LOG.debug("*gate way* routedURI is {}", routedURI);
			resultEntity = proxy.uri(routedURI).post();	
		}
		return resultEntity;
	}



	private ResponseEntity<byte[]> checkServiceURL(String serviceURL, String receivedURI) {
		LOG.debug("*gate way* service URL is {}", serviceURL);
		ResponseEntity<byte[]> res = null;
		if(serviceURL == null) {
			res = ResponseEntity.status(404).body(String.format("URI %s does'n contain proper URN", receivedURI).getBytes());
		}
		return res;
	}

	private String getServiceUrl(String receivedURI) {
		String firstUrnItem = receivedURI.split("/+")[1];
		LOG.debug("*gate way* first item of URN is {}", firstUrnItem);
		return mapOfUrls.get(firstUrnItem);
	}

	@PostConstruct
	void createMapOfUrls() {
		LOG.debug("*gate way* configuration with all URL's {}", urls);
		mapOfUrls = urls.stream().collect(Collectors.toMap(url -> url.split("->")[0], 
				url -> url.split("->")[1]));
		LOG.info("*gate way* name -> service connections {}", mapOfUrls);
	}

}
