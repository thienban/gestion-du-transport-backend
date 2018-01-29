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
	public List<ReserverVehicule> creerReservations(@RequestBody Map<String, Integer> body) {
		Integer reserver_id = body.get("ReserverVehicule_id");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		Annonce annonce = annonceRepo.findOne(annonce_id);
		if (annonce.getNbPlacesRestantes() > 0) {
			List<Collaborateur> passagers = annonce.getPassagers();
			passagers.add(collab);
			annonce.setPassagers(passagers);
			annonceRepo.save(annonce);
		}
		return mesReservations();
	}

}
