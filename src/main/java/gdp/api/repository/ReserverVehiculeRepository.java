package gdp.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gdp.api.entities.Collaborateur;
import gdp.api.entities.ReserverVehicule;

public interface ReserverVehiculeRepository extends JpaRepository<ReserverVehicule, Integer>{
	
	public List<ReserverVehicule> findByDateReservationGreaterThanAndPassagerIsNot(LocalDateTime date, Collaborateur nopassager);
	
	@Query(value = "SELECT * FROM Reserver_Vehicule WHERE option_Chauffeur = true AND chauffeur_id IS NULL AND chauffeur_id IS NOT ?1", nativeQuery = true)
	public List<ReserverVehicule> findToConfirmReservations(Integer chauffeurId);
	
	public List<ReserverVehicule> findByChauffeur(Collaborateur chauffeur);
}
