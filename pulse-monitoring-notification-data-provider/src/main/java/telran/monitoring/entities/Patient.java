package telran.monitoring.entities;

import jakarta.persistence.*;

@Entity
@Table(name="patients")
public class Patient {
	@Id
	@Column(name="patient_id")
	long id;
	
	String name;
	
	public Patient() {
		
	}
	
	public Patient(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		
		return id;
	}
	
	public String getName() {
		
		return name;
	}
	
}
