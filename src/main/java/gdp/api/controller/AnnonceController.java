package gdp.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.StatusCovoit;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.GoogleApiService;
import gdp.api.services.MailJetService;

@RestController
@RequestMapping("annonces")
public class AnnonceController {
	@Autowired
	AnnonceRepository annonceRepo;
	@Autowired
	CollaborateurRepository collabRepo;
	@Autowired
	GoogleApiService googleApiSvc;
	@Autowired
	private MailJetService mailJetSvc;

	@GetMapping
	public List<Annonce> findAllAnnonces() {
		return annonceRepo.findAll();
	}

	/**
	 * Crée une annonce pour l'utilisateur courant
	 */ 
	@PostMapping(path = "/creer")
	public List<Annonce> creerAnnonce(@RequestBody Annonce nouvAnnonce) {
		Collaborateur collab = collabRepo.findByEmail(GetUserEmail());
		nouvAnnonce.setAuteur(collab);
		System.out.println(nouvAnnonce.getAdresseDepart());
		googleApiSvc.populateTrajetInfo(nouvAnnonce);
		nouvAnnonce.setStatusCovoit(StatusCovoit.EN_COURS);
		annonceRepo.save(nouvAnnonce);
		return getUserAnnonces();
	}

	/**
	 * Récupère les annonces de l'utilisateur courant
	 */
	@GetMapping(path = "/me")
	public List<Annonce> getUserAnnonces() {
		String email = GetUserEmail();
		Collaborateur collab = collabRepo.findByEmail(email);
		return annonceRepo.findByAuteur(collab);
	}

	public String GetUserEmail() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PostMapping(path = "/annuler")
	public List<Annonce> cancelAnnonce(@RequestBody Map<String, Integer> body)
			throws MailjetException, MailjetSocketTimeoutException {
		Integer annonce_id = body.get("annonce_id");
		Annonce annonce = annonceRepo.findOne(annonce_id);
		if (annonce.getAuteur().getEmail().equals(GetUserEmail())) {
			annonce.setStatusCovoit(StatusCovoit.ANNULE);
			// put passengers in annulations array
			annonce.setAnnulations(annonce.getPassagers());
			// remove all passengers
			annonce.setPassagers(new ArrayList<Collaborateur>());
			// save annonce
			annonceRepo.save(annonce);
		}
		// send confirmation emails :
		// String monEmail = "alex.behaghel@gmail.com";
		// mailJetSvc.sendResaCancellationEmailTo(collab.getEmail(), annonce);
		// mailJetSvc.sendPassagerCancellationEmailTo(annonce.getAuteur().getEmail(),
		// annonce);
		return getUserAnnonces();
	}

}
