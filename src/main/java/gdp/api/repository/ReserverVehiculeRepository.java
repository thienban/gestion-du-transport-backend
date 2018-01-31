package gdp.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gdp.api.entities.Collaborateur;
import gdp.api.entities.ReserverVehicule;
import gdp.api.entities.VehiculeSociete;

public interface ReserverVehiculeRepository extends JpaRepository<ReserverVehicule, Integer>{
	
	public List<ReserverVehicule> findByDateReservationGreaterThanAndPassagerIsNot(LocalDateTime date, Collaborateur nopassager);
	
	@Query(value = "SELECT * FROM reserver_vehicule WHERE option_chauffeur = 1 AND chauffeur_id IS NULL", nativeQuery = true)
	public List<ReserverVehicule> findToConfirmReservations();
	
	public List<ReserverVehicule> findByChauffeur(Collaborateur chauffeur);
	
	public List<ReserverVehicule> findByVehicule(VehiculeSociete vehicule);
}
