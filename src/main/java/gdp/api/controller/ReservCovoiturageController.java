package gdp.api.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;

@RestController
@RequestMapping("reservations")
public class ReservCovoiturageController {
   
	@Autowired
	private AnnonceRepository annonceRepo;

	@Autowired   
	private CollaborateurRepository collabRepo;

	@GetMapping()
	public List<Annonce> ListAnnonce() {
		return annonceRepo.findAll();
	}

	@GetMapping(path = "/me")
	public List<Annonce> MesReservations() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findAll().stream().filter(annonce -> {
			return annonce.getPassagers().contains(collab);
		}).collect(Collectors.toList());
	}

	@PostMapping(path = "/creer")
	public Annonce CreerReservations(@RequestBody Map<String, Integer> body) {
		Integer annonce_id = body.get("annonce_id");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		Annonce annonce = annonceRepo.findOne(annonce_id);
		if (annonce.getNbPlacesRestantes() > 0) {
			List<Collaborateur> passagers = annonce.getPassagers();
			passagers.add(collab);
			annonce.setPassagers(passagers);
			annonceRepo.save(annonce);
		}
		return annonce;
	}
}
