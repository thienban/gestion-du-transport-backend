package gdp.api.listener;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gdp.api.entities.Role;
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

	@EventListener(ApplicationReadyEvent.class)
	public void initDatabase() {
		AtomicInteger counter = new AtomicInteger();
		http.getService().getCollaborateurs().flatMap(collabs -> Observable.from(collabs)).limit(20).map(collab -> {
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
}
