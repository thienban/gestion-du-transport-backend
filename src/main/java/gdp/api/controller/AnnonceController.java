package gdp.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;

@RestController
@RequestMapping("annonces")
public class AnnonceController {
	@Autowired AnnonceRepository annonceRepo;
	@Autowired CollaborateurRepository collabRepo;
	
	@GetMapping
	public List<Annonce> findAllAnnonces(){
		return annonceRepo.findAll();
	}
	
	@PostMapping(path = "/creer/")
	public List<Annonce> creerAnnonce(@RequestBody Annonce nouvAnnonce) {
		annonceRepo.save(nouvAnnonce);
		return annonceRepo.findAll();
	}
	
	@GetMapping(path="/{matricule}")
	public List<Annonce> annoncesOfId(@PathVariable("matricule") String matricule) {
		Collaborateur collab = collabRepo.findByMatricule(matricule);
		return annonceRepo.findAll().stream().filter(annonce -> {
			return annonce.getAuteur().equals(collab);
		}).collect(Collectors.toList());
	}
	
}
