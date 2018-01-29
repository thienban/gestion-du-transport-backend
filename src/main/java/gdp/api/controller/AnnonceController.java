package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.StatusCovoit;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.GoogleApiService;

@RestController
@RequestMapping("annonces")
public class AnnonceController {
	@Autowired
	AnnonceRepository annonceRepo;
	@Autowired
	CollaborateurRepository collabRepo;
	@Autowired
	GoogleApiService googleApiSvc;

	@GetMapping
	public List<Annonce> findAllAnnonces() {
		return annonceRepo.findAll();
	}

	/**
	 * Crée une annonce pour l'utilisateur courant
	 */
	@PostMapping(path = "/creer")
	public List<Annonce> creerAnnonce(@RequestBody Annonce nouvAnnonce) {
		String email = GetUserEmail();
		Collaborateur collab = collabRepo.findByEmail(email);
		nouvAnnonce.setAuteur(collab);
		System.out.println(nouvAnnonce.getAdresseDepart());
		googleApiSvc.populateTrajetInfo(nouvAnnonce);
		nouvAnnonce.setStatusCovoit(StatusCovoit.EN_COURS);
		annonceRepo.save(nouvAnnonce);
		return getUserAnnonces();
	}

	/**
	 * Récupère les annonces de l'utilisateur courant
	 */
	@GetMapping(path = "/me")
	public List<Annonce> getUserAnnonces() {
		String email = GetUserEmail();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findByAuteur(collab);
	}

	public String GetUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
