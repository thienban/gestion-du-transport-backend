package gdp.api.listener;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import gdp.api.entities.Categorie;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Marque;
import gdp.api.entities.Modele;
import gdp.api.entities.Role;
import gdp.api.entities.StatusVehicule;
import gdp.api.entities.VehiculeCovoit;
import gdp.api.entities.VehiculeSociete;
import gdp.api.repository.AdresseRepository;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CategorieRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.MarqueRepository;
import gdp.api.repository.ModeleRepository;
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
	AdresseRepository adresseRepo;
	
	@Autowired
	MarqueRepository marqueRepo;
	
	@Autowired
	ModeleRepository modeleRepo;
	
	@Autowired 
	CategorieRepository categorieRepo;
	
	@Autowired VehiculeRepository vehiculeRepo;

	@Autowired
	GoogleApiService googleApiSvc;

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
			creerVehiculesSociete();
		});
	}

	private void creerAnnonce() {
		
		// Annonce 1
		Marque m1 = new Marque();
		m1.setLibelle("Citroën");
		marqueRepo.save(m1);
		
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
		
		VehiculeCovoit v1 = new VehiculeCovoit();
		v1.setImmatriculation("NI-236-KB");
		v1.setMarque("citroën");
		v1.setModele("C5");
		v1.setNbPlaces(4);
		
		Collaborateur auteur = collabRepo.findOne(15);
		Annonce annonce = new Annonce();
		annonce.setAuteur(auteur);
		annonce.setDateDepart(LocalDateTime.now().plusDays(15));
		annonce.setVehicule(v1);
		annonce.setAdresseDepart("3 rue de la paix Paris 75018");
		annonce.setAdresseArrive("3 rue de la soif Rennes 44000");

		annonceRepo.save(annonce);
		LOGGER.info("Annonce sauvée");

		LOGGER.info("Ajout de passagers ...");
		List<Collaborateur> passagers = annonce.getPassagers();
		passagers.add(collabRepo.findOne(13));
		passagers.add(collabRepo.findOne(12));
		annonce.setPassagers(passagers);
		
		googleApiSvc.populateTrajetInfo(annonce);
		annonceRepo.save(annonce);

		LOGGER.info("Reservations ajoutées");
		
		for (int i = 1; i<15 ; i++) {
			
			Collaborateur auteur2 = collabRepo.findOne(15);
			Annonce annonce2 = new Annonce();
			annonce2.setAuteur(auteur2);
			LocalDateTime dateHisto1 = LocalDateTime.of(2003, 01, 01, 12, 1, 2);
			annonce2.setDateDepart(dateHisto1);
			annonce2.setAdresseDepart(i+" rue de la paix Paris 75018");
			annonce2.setVehicule(v1);
			annonce2.setAdresseArrive(i+" rue de la soif Rennes 44000");
			
			annonceRepo.save(annonce2);
			LOGGER.info("Annonce sauvée");
			
			
			LOGGER.info("Ajout de passagers ...");

			List<Collaborateur> passagers2 = annonce2.getPassagers();
			passagers2.add(collabRepo.findOne(13));
			passagers2.add(collabRepo.findOne(12));
			annonce2.setPassagers(passagers2);
			googleApiSvc.populateTrajetInfo(annonce2);
			annonceRepo.save(annonce2);
			LOGGER.info("Reservations ajoutées");
			

		}
	
	}
	
	public void creerVehiculesSociete() {

		Marque m2 = new Marque();
		m2.setLibelle("Peugeot");
		marqueRepo.save(m2);
		
		Modele mod = new Modele();
		mod.setLibelle("206");
		modeleRepo.save(mod);
		
		for (int i=1; i<=30; i++) {
			
			VehiculeSociete vehicule = new VehiculeSociete();
			vehicule.setImmatriculation("" + (char)(64 + i%26) + (char)(64 + i%26) + String.format("-%03d-", i) + (char)(64 + i%26) + (char)(64 + i%26));
			vehicule.setMarque(m2);
			vehicule.setModele(mod);
			vehicule.setCategorie(categorieRepo.findOne(4));
			vehicule.setNbPlaces(4);
			vehicule.setStatus(StatusVehicule.EN_SERVICE);
			vehicule.setPhoto("http://1.bp.blogspot.com/-eGIZoc5Pqv8/TcLUcaGjSjI/AAAAAAAAGr0/0TvqE1p8wjY/s1600/car%2Bweapon%2Bmod.jpg");
			
			vehiculeRepo.save(vehicule);
		
		}
		
	}
}
