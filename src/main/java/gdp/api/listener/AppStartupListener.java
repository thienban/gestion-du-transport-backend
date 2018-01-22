package gdp.api.listener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gdp.api.entities.Adresse;
import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Role;
import gdp.api.repository.AdresseRepository;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.HttpService;
import rx.Observable;

@Component
public class AppStartupListener {
	final static Logger LOGGER = LoggerFactory.getLogger(AppStartupListener.class);

	@Autowired
	HttpService http;

	@Autowired
	CollaborateurRepository collabRepo;

	@Autowired
	AnnonceRepository annonceRepo;

	@Autowired
	AdresseRepository adresseRepo;;

	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		LOGGER.info("DataBase Initialisation");
		AtomicInteger counter = new AtomicInteger();
		http.getCollabService().getCollaborateurs(20).flatMap(collabs -> Observable.from(collabs)).map(collab -> {
			counter.incrementAndGet();
			if (counter.get() < 4) {
				collab.setRole(Role.ADMIN);
			} else if (counter.get() <= 10) {
				collab.setRole(Role.CHAUFFEUR);
			}
			return collab;
		}).toList().subscribe(collabs -> {
			collabRepo.save(collabs);
			creerAnnonce();
		});
	}

	private void creerAnnonce() {
		
		// Annonce 1
		Collaborateur auteur = collabRepo.findOne(15);
		Annonce annonce = new Annonce();
		annonce.setAuteur(auteur);
		annonce.setDateDepart(LocalDateTime.now());
		annonce.setNbPlaces(4);
		
		Adresse adresseDepart = new Adresse(1, "rue de la paix", "Paris", 75018);
		adresseRepo.save(adresseDepart);
		annonce.setAdresseDepart(adresseDepart);
		
		Adresse adresseArrivee = new Adresse(1, "rue de la soif", "Rennes", 44000);
		adresseRepo.save(adresseArrivee);
		annonce.setAdresseArrive(adresseArrivee);
		
		annonceRepo.save(annonce);
		LOGGER.info("Annonce sauvée");
		
		
		LOGGER.info("Ajout de passagers ...");

		List<Collaborateur> passagers = annonce.getPassagers();
		passagers.add(collabRepo.findOne(13));
		passagers.add(collabRepo.findOne(12));
		annonce.setPassagers(passagers);
		annonceRepo.save(annonce);
		LOGGER.info("Reservations ajoutées");
		
		for (int i = 2; i<20 ; i++) {
			
			Collaborateur auteur2 = collabRepo.findOne(15);
			Annonce annonce2 = new Annonce();
			annonce2.setAuteur(auteur2);
			
			LocalDateTime dateHisto1 = LocalDateTime.of(2003, 01, 01, 12, 1, 2);
			annonce2.setDateDepart(dateHisto1);
			annonce2.setNbPlaces(4);
			
			Adresse adresseDepart2 = new Adresse(i, "rue de la paix", "Paris", 75018);
			adresseRepo.save(adresseDepart2);
			annonce2.setAdresseDepart(adresseDepart2);
			
			Adresse adresseArrivee2 = new Adresse(i, "rue de la soif", "Rennes", 44000);
			adresseRepo.save(adresseArrivee2);
			annonce2.setAdresseArrive(adresseArrivee2);
			
			annonceRepo.save(annonce2);
			LOGGER.info("Annonce sauvée");
			
			
			LOGGER.info("Ajout de passagers ...");

			List<Collaborateur> passagers2 = annonce2.getPassagers();
			passagers2.add(collabRepo.findOne(13));
			passagers2.add(collabRepo.findOne(12));
			annonce2.setPassagers(passagers2);
			annonceRepo.save(annonce2);
			LOGGER.info("Reservations ajoutées");
		}
	
	}
}
