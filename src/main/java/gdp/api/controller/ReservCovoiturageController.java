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

import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.StatusCovoit;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.MailJetService;

@RestController
@RequestMapping("reservations")
public class ReservCovoiturageController {

	@Autowired
	private AnnonceRepository annonceRepo;

	@Autowired
	private CollaborateurRepository collabRepo;

	@Autowired
	private MailJetService mailJetSvc;

	@GetMapping()
	public List<Annonce> ListAnnonce() {
		return annonceRepo.findAll();
	}

	/**
	 * retourne les annonces dont l'utilisateur courant est passager
	 */
	@GetMapping(path = "/me")
	public List<Annonce> mesReservations() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findAll().stream().filter(annonce -> {
			return annonce.getPassagers().contains(collab) || annonce.getAnnulations().contains(collab);
		}).collect(Collectors.toList());
	}

	/**
	 * retourne les annonces sur lesquelles l'utilisateur courant peut effectuer une
	 * réservation
	 */
	@GetMapping(path = "/available")
	public List<Annonce> reservationsDisponibles() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findByDateDepartGreaterThanAndAuteurIsNot(LocalDateTime.now(), collab).stream()
				.filter(annonce -> {
					return !annonce.getPassagers().contains(collab)
							&& annonce.getVehicule().getNbPlaces() > annonce.getPassagers().size()
							&& !StatusCovoit.ANNULE.equals(annonce.getStatusCovoit());
				}).collect(Collectors.toList());
	}

	/**
	 * crée une réservation : ajoute l'utilisateur courant dans la liste des
	 * passagers de l'annonce dont l'id est passé dans le corps de la requete
	 */
	@PostMapping(path = "/creer")
	public List<Annonce> creerReservation(@RequestBody Map<String, Integer> body) {
		Integer annonce_id = body.get("annonce_id");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		Annonce annonce = annonceRepo.findOne(annonce_id);

		if (annonce.getNbPlacesRestantes() > 0) {
			// add collab to passenger list :
			List<Collaborateur> passagers = annonce.getPassagers();
			passagers.add(collab);
			annonce.setPassagers(passagers);

			// remove collab from cancellation list :
			List<Collaborateur> annulations = annonce.getAnnulations();
			annulations.remove(collab);
			annonce.setAnnulations(annulations);

			// save annonce
			annonceRepo.save(annonce);
		}
		return mesReservations();
	}

	/**
	 * annule une reservation
	 * @param body {annonce_id : value}
	 * @return
	 * @throws MailjetException
	 * @throws MailjetSocketTimeoutException
	 */
	@PostMapping(path = "/annuler")
	public List<Annonce> cancelReservation(@RequestBody Map<String, Integer> body)
			throws MailjetException, MailjetSocketTimeoutException {
		Integer annonce_id = body.get("annonce_id");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collaborateur collab = collabRepo.findByEmail(email);
		Annonce annonce = annonceRepo.findOne(annonce_id);

		// remove collab from passenger list
		List<Collaborateur> passagers = annonce.getPassagers();
		passagers.remove(collab);
		annonce.setPassagers(passagers);

		// Add collab to cancellation list :
		List<Collaborateur> annulations = annonce.getAnnulations();
		annulations.add(collab);
		annonce.setAnnulations(annulations);

		// save annonce
		annonceRepo.save(annonce);

		// send confirmation emails :
		// String monEmail = "alex.behaghel@gmail.com";
		mailJetSvc.sendResaCancellationEmailTo(collab.getEmail(), annonce);
		mailJetSvc.sendPassagerCancellationEmailTo(annonce.getAuteur().getEmail(), annonce);

		return mesReservations();
	}
}
