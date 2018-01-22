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
		http.getService().getCollaborateurs(20).flatMap(collabs -> Observable.from(collabs)).map(collab -> {
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
		Collaborateur auteur = collabRepo.findOne(15);
		Annonce annonce = new Annonce();
		annonce.setAuteur(auteur);
		annonce.setDateDepart(LocalDateTime.now());
		annonce.setNbPlaces(4);
		Adresse adresseDepart = new Adresse(3, "rue de la paix", "Paris", 75018);
		adresseRepo.save(adresseDepart);
		annonce.setAdresseDepart(adresseDepart);
		
		Adresse adresseArrivee = new Adresse(3, "rue de la soif", "Rennes", 44000);
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
	}
}
