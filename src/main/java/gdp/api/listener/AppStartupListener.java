package gdp.api.listener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gdp.api.entities.Annonce;
import gdp.api.entities.Categorie;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Marque;
import gdp.api.entities.Role;
import gdp.api.entities.VehiculeCovoit;
import gdp.api.repository.AdresseRepository;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CategorieRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.MarqueRepository;
import gdp.api.services.GoogleApiService;
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
	AdresseRepository adresseRepo;

	@Autowired
	MarqueRepository marqueRepo;

	@Autowired
	CategorieRepository categorieRepo;

	@Autowired
	GoogleApiService googleApiSvc;

	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		LOGGER.info("DataBase Initialisation");
		AtomicInteger counter = new AtomicInteger();
		http.getCollabService().getCollaborateurs(30).flatMap(collabs -> Observable.from(collabs)).map(collab -> {
			counter.incrementAndGet();
			if (counter.get() < 4) {
				collab.setRole(Role.ADMIN);
			} else if (counter.get() <= 13) {
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
		Marque m1 = new Marque();
		m1.setLibelle("Citroën");
		marqueRepo.save(m1);

		Categorie cat1 = new Categorie();
		cat1.setLibelle("Compactes");
		categorieRepo.save(cat1);

		VehiculeCovoit v1 = new VehiculeCovoit();
		v1.setImmatriculation("NI-236-KB");
		v1.setMarque("citroën");
		v1.setModele("C5");
		v1.setNbPlaces(4);

		List<String> adresses = Arrays.asList("7637 Studebaker Drive, Pasco, WA, United States",
				"73 Elizabeth Avenue Southeast, Auburn, WA, United States", "779 Snake Hill Road, RI, United States",
				"61 George Avenue, NJ, United States", "412 South Ridge Lane, Berlin, CT, United States",
				"48 Vine Avenue, Highland Park, IL, United States", "281 Redwood Drive, Lancaster, PA, United States");

		Random random = new Random();
		for (int i = 0; i < 20; i++) {

			Collaborateur auteur = collabRepo.findOne(i);
			Annonce annonce = new Annonce();
			annonce.setAuteur(auteur);
			if (i <= 7) {
				annonce.setDateDepart(LocalDateTime.now().plusDays(i));
			} else {
				annonce.setDateDepart(LocalDateTime.now().minusDays(i));
			}

			annonce.setVehicule(v1);

			int adresseIndex1 = random.nextInt(adresses.size());
			int adresseIndex2 = random.nextInt(adresses.size());
			while (adresseIndex1 == adresseIndex2) {
				adresseIndex2 = random.nextInt(adresses.size());
			}

			annonce.setAdresseDepart(adresses.get(adresseIndex1));
			annonce.setAdresseArrive(adresses.get(adresseIndex2));

			List<Collaborateur> passagers = annonce.getPassagers();

			int randomNumber1 = random.nextInt(16);
			while (randomNumber1 == i) {
				randomNumber1 = random.nextInt(16);
			}
			int randomNumber2 = random.nextInt(16);
			while (randomNumber2 == randomNumber1 || randomNumber2 == i) {
				randomNumber2 = random.nextInt(16);
			}

			passagers.add(collabRepo.findOne(randomNumber1));
			passagers.add(collabRepo.findOne(randomNumber2));

			annonce.setPassagers(passagers);

			googleApiSvc.populateTrajetInfo(annonce);
			annonceRepo.save(annonce);

		}
		LOGGER.info("Annonces sauvées");
		/*
		 * for (int i = 1; i < 7; i++) {
		 * 
		 * Collaborateur auteur2 = collabRepo.findOne(15); Annonce annonce2 = new
		 * Annonce(); annonce2.setAuteur(auteur2); LocalDateTime dateHisto1 =
		 * LocalDateTime.of(2003, 01, 01, 12, 1, 2); annonce2.setDateDepart(dateHisto1);
		 * annonce2.setAdresseDepart(i + " rue de la paix Paris 75018");
		 * annonce2.setVehicule(v1); annonce2.setAdresseArrive(i +
		 * " rue de la soif Rennes 44000");
		 * 
		 * annonceRepo.save(annonce2); LOGGER.info("Annonce sauvée");
		 * 
		 * LOGGER.info("Ajout de passagers ...");
		 * 
		 * List<Collaborateur> passagers2 = annonce2.getPassagers();
		 * passagers2.add(collabRepo.findOne(13));
		 * passagers2.add(collabRepo.findOne(12)); annonce2.setPassagers(passagers2);
		 * googleApiSvc.populateTrajetInfo(annonce2); annonceRepo.save(annonce2);
		 * LOGGER.info("Reservations ajoutées"); }
		 */
	}
}
