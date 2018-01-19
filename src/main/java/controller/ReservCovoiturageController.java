package controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import entite.ReservCovoiturage;
import repository.ReservCovoiturageRepository;

@Controller
@RequestMapping("/collaborateur/reservations/")
public class ReservCovoiturageController {
	
	@Autowired
	private ReservCovoiturageRepository reRepo;

	/*
	 * @GetMapping public List<ReservCovoiturage> ListeReservCovoiturage(){
	 * 
	 * return ; }
	 */
	
	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public List<ReservCovoiturage> listerReservation (@RequestParam("AdrDep") String AdresseDepart,
			@RequestParam("AdrDes") String AdresseDestination,
			@RequestParam("DateDep") LocalDateTime DateDepart ){
		return reRepo.findByAdresseDepartAndAdresseArriveeAndDateDepart(AdresseDepart, AdresseDestination, DateDepart);
		
	}
}
