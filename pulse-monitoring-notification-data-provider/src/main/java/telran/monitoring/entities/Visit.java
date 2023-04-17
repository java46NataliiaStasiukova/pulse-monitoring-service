package telran.monitoring.entities;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name="visits", indexes = {@Index(columnList = "patient_id")})//quick search by patient id
public class Visit {
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)//strategy that configure database
    long id;
	
	@ManyToOne
	@JoinColumn(name = "doctor_email")
	Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	Patient patient;
	
    LocalDate date;
    
    public Visit() {
    	
    }

    public Visit(Doctor doctor, Patient patient, LocalDate date) {
    	this.doctor = doctor;
    	this.patient = patient;
		this.date = date;
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
