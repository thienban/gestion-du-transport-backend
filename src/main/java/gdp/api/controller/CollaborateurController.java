package gdp.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Collaborateur;
import gdp.api.entities.Role;
import gdp.api.repository.CollaborateurRepository;

@RestController
@RequestMapping("collaborateurs")
public class CollaborateurController {
	@Autowired
	CollaborateurRepository collabRepo;
	
	@GetMapping
	public List<Collaborateur> findAllCollabs(){
		return collabRepo.findAll();
	}
	
	@GetMapping(path = "/chauffeurs")
	public List<Collaborateur> findAllChauffeurs(){
		return collabRepo.findAll().stream()
						 		   .filter(c -> c.getRole().equals(Role.CHAUFFEUR) || c.getRole().equals(Role.ADMIN))
						 		   .collect(Collectors.toList());
	}
	
	@PostMapping(path="/chauffeurs/creer")
	public List<Collaborateur> creerChauffeur(@RequestBody String matricule){
		
		Collaborateur collab = new Collaborateur();
		
		collab = collabRepo.findByMatricule(matricule);
		collab.setRole(Role.CHAUFFEUR);
		
		collabRepo.save(collab);
		
		return findAllChauffeurs();

	}
}
