package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Categorie;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Role;
import gdp.api.entities.VehiculeSociete;
import gdp.api.repository.CategorieRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.VehiculeRepository;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	VehiculeRepository vehiculeRepo;
	@Autowired
	CollaborateurRepository collabRepo;
	@Autowired
	CategorieRepository categRepo;
	
	@GetMapping(path="/vehicules")
	public List<VehiculeSociete> findAllVehicules(){
		return vehiculeRepo.findAll();
	}
	
	@GetMapping(path="/vehicules/categories")
	public List<Categorie> findAllCategories(){
		return categRepo.findAll();
	}
	
	
	@GetMapping(path="/chauffeurs")
	public List<Collaborateur> findAllChauffeurs(){
		return collabRepo.findByRole(Role.CHAUFFEUR);
	}
}
