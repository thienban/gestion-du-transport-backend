package gdp.api.controller;

import java.time.LocalDateTime;
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

	/**
	 * retourne les annonces dont l'utilisateur courant est passager
	 */
	@GetMapping(path = "/me")
	public List<Annonce> mesReservations() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findAll().stream().filter(annonce -> {
			return annonce.getPassagers().contains(collab);
		}).collect(Collectors.toList());
	}

	/**
	 * retourne les annonces sur lesquelles l'utilisateur courant peut effectuer une
	 * réservation
	 */
	@GetMapping(path = "/available")
	public List<Annonce> reservationsDisponibles() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findByDateDepartGreaterThanAndAuteurIsNot(LocalDateTime.now(), collab).stream()
				.filter(annonce -> {
					return !annonce.getPassagers().contains(collab);
				}).collect(Collectors.toList());
	}

	/**
	 * crée une réservation : ajoute l'utilisateur courant dans la liste des
	 * passagers de l'annonce dont l'id est passé dans le corps de la requete
	 */
	@PostMapping(path = "/creer")
	public List<Annonce> creerReservations(@RequestBody Map<String, Integer> body) {
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
		return mesReservations();
	}
}
