package gdp.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.ReserverVehicule;
import gdp.api.entities.ReserverVehiculeFront;
import gdp.api.entities.VehiculeSociete;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReserverVehiculeRepository;
import gdp.api.repository.VehiculeRepository;

@RestController
@RequestMapping("vehicules")
public class ReservVehiculeController {
	
	@Autowired
	private ReserverVehiculeRepository reserverRepo;
	
	@Autowired
	private CollaborateurRepository collabRepo;
	
	@Autowired
	private VehiculeRepository vehiculeRepo;
	
	@GetMapping()
	public List<ReserverVehicule> ListVehicule() {
		return reserverRepo.findAll();
	}
	
	/**
	 * crée une réservation : ajoute l'utilisateur courant dans la liste des
	 * passagers de l'annonce dont l'id est passé dans le corps de la requete
	 * ajoute le vehicule
	 * alerter les chauffeurs
	 */
	@PostMapping(path = "/creer")
	public List<ReserverVehicule> creerReservations(@RequestBody ReserverVehiculeFront reservation) {
		Integer reserver_id = reservation.getId_vehicule()
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Collaborateur passager = collabRepo.findByEmail(email);
		VehiculeSociete vehicule = vehiculeRepo.findOne(reserver_id);
		ReserverVehicule reserver = reserverRepo.findOne(reserver_id);
		
		if (!reserver.isOptionChauffeur()) {
			
			reserver.setPassager(passager);
			reserver.setVehicule(vehicule);
			reserver.setDisponible(false);
			
			reserverRepo.save(reserver);
			//ajouter une voiture, passager, creer la reservation
		}
		return mesReservations();
	}

}
