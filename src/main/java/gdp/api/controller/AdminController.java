package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Categorie;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Marque;
import gdp.api.entities.Modele;
import gdp.api.entities.Role;
import gdp.api.entities.StatusVehicule;
import gdp.api.entities.VehiculeSociete;
import gdp.api.repository.CategorieRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.MarqueRepository;
import gdp.api.repository.ModeleRepository;
import gdp.api.repository.VehiculeRepository;

@RestController
@RequestMapping("admin")
public class AdminController {
	@Autowired
	VehiculeRepository vehiculeRepo;
	@Autowired
	CollaborateurRepository collabRepo;
	@Autowired
	MarqueRepository marqueRepo;
	@Autowired
	ModeleRepository modeleRepo;
	@Autowired
	CategorieRepository categRepo;
	
	@GetMapping(path="/vehicules")
	public List<VehiculeSociete> findAllVehicules(){
		return vehiculeRepo.findAll();
	}
	
	@GetMapping(path="/vehicules/{immat}")
	public VehiculeSociete findVehiculeByImmat(@PathVariable(name="immat") String immat) {
		return vehiculeRepo.findByImmatriculation(immat);
	}
	
	@GetMapping(path="/vehicules/categories")
	public List<Categorie> findAllCategories(){
		return categRepo.findAll();
	}
	
	
	@PostMapping(path="/vehicules/marque")
	public Marque createOrGetMarque(@RequestBody String marque) {
		//Si cette marque existe déjà, la renvoyer
		if (marqueRepo.findByLibelle(marque) != null) {
			return marqueRepo.findByLibelle(marque);
		//sinon, la créer en base
		} else {
			Marque newMarque = new Marque();
			newMarque.setLibelle(marque);
			marqueRepo.save(newMarque);
			return newMarque;			
		}
	}
	
	@PostMapping(path="/vehicules/modele")
	public Modele createOrGetModele(@RequestBody String modele) {
		//Si ce modèle existe déjà, le renvoyer
		if (modeleRepo.findByLibelle(modele) != null) {
			return modeleRepo.findByLibelle(modele);
		//sinon, le créer en base
		} else {
			Modele newModele = new Modele();
			newModele.setLibelle(modele);
			modeleRepo.save(newModele);
			return newModele;			
		}
		
	}
	
	@PostMapping(path="/vehicules/creer")
	public List<VehiculeSociete> creerVehicule(@RequestBody VehiculeSociete nouvVehicule) {
		nouvVehicule.setStatus(StatusVehicule.EN_SERVICE);
		vehiculeRepo.save(nouvVehicule);
		return findAllVehicules();
	}
	
	@GetMapping(path="/chauffeurs")
	public List<Collaborateur> findAllChauffeurs(){
		return collabRepo.findByRole(Role.CHAUFFEUR);
	}
	
}
