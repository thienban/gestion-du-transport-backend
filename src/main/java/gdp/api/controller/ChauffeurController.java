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

import gdp.api.entities.Collaborateur;
import gdp.api.entities.ReserverVehicule;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReserverVehiculeRepository;

@RestController
@RequestMapping("chauffeurs")
public class ChauffeurController {
	
	@Autowired
	private ReserverVehiculeRepository reserverRepo;
	
	@Autowired
	private CollaborateurRepository collabRepo;
	
	
	@GetMapping()
	public List<ReserverVehicule> aConfirmer() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return reserverRepo.findToConfirmReservations(collabRepo.findByEmail(email).getId());
	}
	
	@GetMapping(path="/me")
	public List<ReserverVehicule> mesCourses() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return reserverRepo.findByChauffeur(collabRepo.findByEmail(email));
	}
	
	@PostMapping(path="/accept")
	public ReserverVehicule courseAccepte(@RequestBody Map<String, Integer> body) {
		Integer reservation_id = body.get("reservationVehicule_id");
		ReserverVehicule reservation = reserverRepo.findOne(reservation_id);
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur chauffeur =  collabRepo.findByEmail(email);
		reservation.setChauffeur(chauffeur);
		reserverRepo.save(reservation);
		return reservation;
	}
}
