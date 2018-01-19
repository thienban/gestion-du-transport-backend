package gdp.api.listener;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import entite.ReservCovoiturage;
import entite.StatusCovoit;
import gdp.api.entities.Role;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.repository.ReservCovoiturageRepository;
import gdp.api.services.HttpService;
import rx.Observable;

@Component
public class AppStartupListener {
	@Autowired
	HttpService http;

	@Autowired
	CollaborateurRepository collabRepo;
	
	@Autowired
	ReservCovoiturageRepository reservCovoitRepo;

	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		AtomicInteger counter = new AtomicInteger();
		http.getService().getCollaborateurs().limit(20).flatMap(collabs -> Observable.from(collabs)).map(collab -> {
			counter.incrementAndGet();
			if (counter.get() < 4) {
				collab.setRole(Role.ADMIN);
			} else if (counter.get() <= 10) {
				collab.setRole(Role.CHAUFFEUR);
			}
			return collab;
		}).toList().subscribe(collabs -> {
			collabRepo.save(collabs);
			
		});
	}
	
	public void initDatabaseWithReservations() {
		
		
	}
}
