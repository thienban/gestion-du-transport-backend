package gdp.api.listener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.ArrayList;
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

import gdp.api.entities.ReserverVehicule;
import gdp.api.entities.Role;
import gdp.api.entities.StatusVehicule;
import gdp.api.entities.VehiculeCovoit;
import gdp.api.entities.VehiculeSociete;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CategorieRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.MarqueRepository;
import gdp.api.repository.ModeleRepository;
import gdp.api.repository.ReserverVehiculeRepository;
import gdp.api.repository.VehiculeRepository;
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
	MarqueRepository marqueRepo;

	@Autowired
	ModeleRepository modeleRepo;

	@Autowired
	CategorieRepository categorieRepo;

	@Autowired
	VehiculeRepository vehiculeRepo;

	@Autowired
	ReserverVehiculeRepository reserverVRepo;

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
			creerVehiculesSociete();
			creerReservationVehicule();
		});
	}

	private void creerAnnonce() {
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
		for (int i = 1; i < 20; i++) {

			Collaborateur auteur = collabRepo.findOne(i);
			Annonce annonce = new Annonce();
			annonce.setAuteur(auteur);
			if (i <= 7) {
				annonce.setDateDepart(LocalDateTime.now().plusDays(i));
				annonce.setDateArrivee(LocalDateTime.now().plusDays(i + 10));
			} else {
				annonce.setDateDepart(LocalDateTime.now().minusDays(i));
				annonce.setDateArrivee(LocalDateTime.now().minusDays(i + 10));
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

		LOGGER.info("Reservations ajoutées");
		LOGGER.info("Annonces sauvées");

	}

	public void creerVehiculesSociete() {
		List<Categorie> categories = new ArrayList<Categorie>();
		categories.add(new Categorie("Micro-urbaines"));
		categories.add(new Categorie("Mini-citadines"));
		categories.add(new Categorie("Citadines polyvalentes"));
		categories.add(new Categorie("Compactes"));
		categories.add(new Categorie("Berlines taille S"));
		categories.add(new Categorie("Berlines taille M"));
		categories.add(new Categorie("Berlines taille L"));
		categories.add(new Categorie("SUV, Tout-terrains et Pick-ups"));
		categorieRepo.save(categories);

		for (int i = 1; i <= 15; i++) {

			VehiculeSociete vehicule = new VehiculeSociete();
			vehicule.setImmatriculation("" + (char) (64 + (i % 26 + 1)) + (char) (64 + (i % 26 + 1))
					+ String.format("-%03d-", i) + (char) (64 + (i % 26 + 1)) + (char) (64 + (i % 26 + 1)));
			vehicule.setMarque("Peugeot");
			vehicule.setModele("206");
			vehicule.setCategorie(categorieRepo.findOne(4));
			vehicule.setNbPlaces(4);
			vehicule.setStatus(StatusVehicule.EN_SERVICE);
			vehicule.setPhoto(
					"http://1.bp.blogspot.com/-eGIZoc5Pqv8/TcLUcaGjSjI/AAAAAAAAGr0/0TvqE1p8wjY/s1600/car%2Bweapon%2Bmod.jpg");
			vehiculeRepo.save(vehicule);
		}
		LOGGER.info("véhicules sauvées");

	}

	public void creerReservationVehicule() {
		LOGGER.info("creerReservationVehicule");
		for (int i = 1; i <= 3; i++) {
			LOGGER.info(Math.round(24 / i) + "");

			ReserverVehicule reserV = new ReserverVehicule();
			reserV.setPassager(collabRepo.findOne(i));
			reserV.setOptionChauffeur(true);
			int hourOfDep = Math.round(12 / i);
			LocalDateTime depart = LocalDateTime.of(LocalDate.now(), LocalTime.of(hourOfDep, 15));
			LOGGER.info(depart.toString());

			reserV.setDateReservation(depart);
			reserV.setDateRetour(depart.plusHours(2));
			reserV.setVehicule(vehiculeRepo.findOne(i));
			reserverVRepo.save(reserV);
		}

		ReserverVehicule reserV = reserverVRepo.findOne(1);
		reserV.setChauffeur(collabRepo.findOne(1));
		reserverVRepo.save(reserV);
		LOGGER.info("Resa véhicules sauvées");
	}
}
