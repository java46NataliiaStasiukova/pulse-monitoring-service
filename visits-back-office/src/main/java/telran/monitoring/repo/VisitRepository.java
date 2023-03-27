package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.monitoring.entities.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {

}
