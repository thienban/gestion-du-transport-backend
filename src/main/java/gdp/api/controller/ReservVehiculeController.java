package gdp.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
	public List<ReserverVehicule> creerReservations(@RequestBody ReserverVehiculeFront reservation) {
		Integer vehicule_id = reservation.getId_vehicule();
		LocalDateTime dateReservation = reservation.getDateReservation();
		LocalDateTime dateRetour = reservation.getDateRetour();
		Boolean optionChauffeur = reservation.isOptionChauffeur();
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Collaborateur passager = collabRepo.findByEmail(email);
		VehiculeSociete vehicule = vehiculeRepo.findOne(vehicule_id);
		
		ReserverVehicule reserver = new ReserverVehicule();
		reserver.setPassager(passager);
		reserver.setVehicule(vehicule);
		reserver.setDateReservation(dateReservation);
		reserver.setDateRetour(dateRetour);
		reserver.setDisponible(false);
		reserver.setOptionChauffeur(optionChauffeur);

		reserverRepo.save(reserver);
		return mesReservations();
	}

}
