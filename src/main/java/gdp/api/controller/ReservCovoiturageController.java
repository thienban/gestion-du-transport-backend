package gdp.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping(path = "/{matricule}")
	public List<Annonce> MesReservations(@PathVariable("matricule") String matricule) {
		Collaborateur collab = collabRepo.findByMatricule(matricule);
		return annonceRepo.findAll().stream().filter(annonce -> {
			return annonce.getPassagers().contains(collab);
		}).collect(Collectors.toList());
	}

	@PostMapping(path = "/creer/{matricule}")
	public Annonce CreerReservations(@PathVariable("matricule") String matricule,
			@Param("annonce_id") Integer annonce_id) {
		Collaborateur collab = collabRepo.findByMatricule(matricule);
		Annonce annonce = annonceRepo.getOne(annonce_id);
		if (annonce.getNbPlacesRestantes() > 0) {
			List<Collaborateur> passagers = annonce.getPassagers();
			passagers.add(collab);
			annonce.setPassagers(passagers);
			annonceRepo.save(annonce);
		}
		return annonce;
	}
}
