package telran.monitoring.entities;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;



@RedisHash
public class ProbesList {
	
	@Id
	long patientId;
	List<Integer> pulseValues = new ArrayList<>();

	public ProbesList(long patientId) {
		super();
		this.patientId = patientId;
		//this.pulseValues = pulseValues;
	}
	
	public ProbesList() {
		
	}

	public List<Integer> getPulseValues() {
		return pulseValues;
	}

	public void setPulseValues(List<Integer> pulseValues) {
		this.pulseValues = pulseValues;
	}

	public long getPatientId() {
		return patientId;
	}

}
