package telran.monitoring.entities;

import jakarta.persistence.*;

@Entity
@Table(name="doctors")
public class Doctor {
	@Id
	@Column(name = "doctor_email")
	String email;
	String name;
	
	public Doctor() {
		
	}

	public Doctor(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Doctor [email=" + email + ", name=" + name + "]";
	}
	
	public String getEmail() {
		
		return email;
	}
	
	public String getName() {
		
		return name;
	}

}
