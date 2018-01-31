package gdp.api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * retourne les reservations dont l'utilisateur courant est passager
	   ReserverVehicule
	 */
	@GetMapping(path = "/me")
	public List<ReserverVehicule> mesReservations() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return reserverRepo.findAll().stream().filter(reservation -> {
			return reservation.getPassager().getMatricule().equals(collab.getMatricule());
		}).collect(Collectors.toList());
	}
	/**
	 * crée une réservation : récupérer les données de ReserverVehiculeFront 
	 * puis sauvegarder la nouvelle réservation
	 */
	@PostMapping(path = "/creer")
	public List<ReserverVehicule> creerReservations(@RequestBody ReserverVehicule reservation) {
		Integer vehicule_id = reservation.getVehicule().getId();
		LocalDateTime dateReservation = reservation.getDateReservation();
		LocalDateTime dateRetour = reservation.getDateRetour();
		Boolean optionChauffeur = reservation.isOptionChauffeur();
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Collaborateur passager = collabRepo.findByEmail(email);
		VehiculeSociete vehicule = vehiculeRepo.findOne(vehicule_id);
		vehicule.setDisponible(false);
		
		ReserverVehicule reserver = new ReserverVehicule();
		reserver.setPassager(passager);
		reserver.setVehicule(vehicule);
		reserver.setDateReservation(dateReservation);
		reserver.setDateRetour(dateRetour);
		reserver.setOptionChauffeur(optionChauffeur);

		reserverRepo.save(reserver);
		vehiculeRepo.save(vehicule);
		return mesReservations();
	}
	
	@GetMapping(path = "/available")
	public List<VehiculeSociete> vehiculesDispos() {
		
		return vehiculeRepo.findAll();
	}
	
	@GetMapping(path="/{immat}")
	public List<ReserverVehicule> reservationsByVehicule(@PathVariable String immat) {
		VehiculeSociete veh = vehiculeRepo.findByImmatriculation(immat);
		if (veh != null) {			
			return reserverRepo.findByVehicule(veh);
		} else {
			return new ArrayList();
		}
	}

}
