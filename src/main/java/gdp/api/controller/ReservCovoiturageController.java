package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import gdp.api.entities.ReservCovoiturage;
import gdp.api.repository.ReservCovoiturageRepository;

@Controller
@RequestMapping("/collaborateur/reservations/")
public class ReservCovoiturageController {

	@Autowired
	private ReservCovoiturageRepository reservCovoitRepo;
	
	@GetMapping
	public List<ReservCovoiturage> ListeReservCovoiturage(){
		
		return reservCovoitRepo.findAll();
	}
}
