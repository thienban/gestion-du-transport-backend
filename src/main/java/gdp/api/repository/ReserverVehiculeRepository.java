package gdp.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Collaborateur;
import gdp.api.entities.ReserverVehicule;

public interface ReserverVehiculeRepository extends JpaRepository<ReserverVehicule, Integer>{
	
	public List<ReserverVehicule> findByDateReservationGreaterThanAndPassagerIsNot(LocalDateTime date, Collaborateur nopassager);
}
