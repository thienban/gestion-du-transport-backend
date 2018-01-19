package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Collaborateur;
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
}
