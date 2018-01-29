package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.ReserverVehicule;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReserverVehiculeRepository;

@RestController
@RequestMapping("chauffeur")
public class ChauffeurController {
	
	@Autowired
	private ReserverVehiculeRepository reserverRepo;
	
	@Autowired
	private CollaborateurRepository collabRepo;
	
	
	@GetMapping()
	public List<ReserverVehicule> aConfirmer() {
		return reserverRepo.findToConfirmReservations();
	}
	
	@GetMapping(path="/me")
	public List<ReserverVehicule> mesCourses() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return reserverRepo.findByChauffeur(collabRepo.findByEmail(email));
	}
	
}
