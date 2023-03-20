package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.monitoring.entities.Visit;
import telran.monitoring.model.NotificationData;

public interface VisitRepository extends JpaRepository<Visit, Long> {

	//SQL
//	@Query(value = "select d.did, d.name, p.name from visits v join patients p on p.pid=v.pid \n"
//			+ "join doctors d on v.did=v.did \n"
//			+ "where p.pid = :patientId order by v.date desc limit 1", 
//			nativeQuery = true)
	@Query(value = "select doctor.did, doctor.name, patient.name from visits visit join patients patient on patient.pid=visit.pid \n"
			+ "join doctors doctor on visit.did=visit.did \n"
			+ "where patient.pid = :patientId order by visit.date desc limit 1", 
			nativeQuery = true)
	NotificationData getPatientLastVisitById(long patientId);

}
