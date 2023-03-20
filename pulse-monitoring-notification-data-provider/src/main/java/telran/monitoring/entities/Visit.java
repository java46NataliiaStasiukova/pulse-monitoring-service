package telran.monitoring.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="visits")
public class Visit {
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
	
	@ManyToOne
	@JoinColumn(name = "did")
	Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "pid")
	Patient patient;
	
    LocalDate date;
    
    public Visit() {
    	
    }

    public Visit(Doctor doctor, Patient patient, LocalDate date) {
    	this.doctor = doctor;
    	this.patient = patient;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Visit [id=" + id + ", date=" + date + "]";
	}
	
	public long getId() {
		
		return id;
	}
	
	public Doctor getDoctor() {
		
		return doctor;
	}
	
	public Patient getPatient() {
		
		return patient;
	}
	
	public LocalDate getDate() {
		
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}

	
}
