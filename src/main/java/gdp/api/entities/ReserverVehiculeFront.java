package gdp.api.entities;

import java.time.LocalDateTime;

public class ReserverVehiculeFront {
	
	
	private Integer id_vehicule;
	
	private LocalDateTime dateReservation;
	
	private LocalDateTime dateRetour;
	
	private boolean optionChauffeur;

	public Integer getId_vehicule() {
		return id_vehicule;
	}

	public void setId_vehicule(Integer id_vehicule) {
		this.id_vehicule = id_vehicule;
	}

	public LocalDateTime getDateReservation() {
		return dateReservation;
	}

	public void setDateReservation(LocalDateTime dateReservation) {
		this.dateReservation = dateReservation;
	}

	public LocalDateTime getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(LocalDateTime dateRetour) {
		this.dateRetour = dateRetour;
	}

	public boolean isOptionChauffeur() {
		return optionChauffeur;
	}

	public void setOptionChauffeur(boolean optionChauffeur) {
		this.optionChauffeur = optionChauffeur;
	}
	
}
