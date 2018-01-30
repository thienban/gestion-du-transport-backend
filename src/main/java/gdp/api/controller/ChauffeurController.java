package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.ReserverVehicule;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReserverVehiculeRepository;
import gdp.api.repository.VehiculeRepository;

@RestController
@RequestMapping("chauffeur")
public class ChauffeurController {
	
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

}
