package telran.monitoring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.monitoring.model.VisitDto;
import telran.monitoring.entities.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {


	@Query(value = "select * from visits where patient_id=:patientId", nativeQuery = true)
	List<VisitDto> findVisitsByPatientId(long patientId);

}
