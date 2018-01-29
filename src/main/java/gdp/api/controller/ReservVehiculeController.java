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
import gdp.api.entities.ReserverVehicule;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReserverVehiculeRepository;
import gdp.api.repository.VehiculeRepository;

@RestController
@RequestMapping("vehicules")
public class ReservVehiculeController {
	
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
	
	/**
	 * retourne les reservations véhicule dont l'utilisateur courant est passager
	 */
	@GetMapping(path = "/me")
	public List<ReserverVehicule> mesReservations() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return reserverRepo.findAll().stream().filter(reservation -> {
			return reservation.getPassager() == collab;
		}).collect(Collectors.toList());
	}
	
	/**
	 * retourne les annonces sur lesquelles l'utilisateur courant peut effectuer une
	 * réservation
	 */
	@GetMapping(path = "/available")
	public List<ReserverVehicule> reservationsDisponibles() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return reserverRepo.findByDateReservationGreaterThanAndPassagerIsNot(LocalDateTime.now(), collab).stream()
				.filter(reservation -> {
					return !(reservation.getPassager() == collab && reservation.isDisponible() == false);
				}).collect(Collectors.toList());
	}

}
