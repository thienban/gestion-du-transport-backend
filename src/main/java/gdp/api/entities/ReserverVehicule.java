package gdp.api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "reserver_vehicule")
public class ReserverVehicule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Collaborateur passager;
	
	@ManyToOne
	private Collaborateur chauffeur;
	
	@ManyToOne
	private VehiculeSociete vehicule;
	
	@Column(name="OPTION_CHAUFFEUR")
	private boolean optionChauffeur;
	
	@Column(nullable=false)
	private LocalDateTime dateReservation;
	

	@Column(nullable=false)
	private LocalDateTime dateRetour;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Collaborateur getPassager() {
		return passager;
	}


	public void setPassager(Collaborateur passager) {
		this.passager = passager;
	}


	public Collaborateur getChauffeur() {
		return chauffeur;
	}


	public void setChauffeur(Collaborateur chauffeur) {
		this.chauffeur = chauffeur;
	}


	public VehiculeSociete getVehicule() {
		return vehicule;
	}


	public void setVehicule(VehiculeSociete vehicule) {
		this.vehicule = vehicule;
	}


	public boolean isOptionChauffeur() {
		return optionChauffeur;
	}


	public void setOptionChauffeur(boolean optionChauffeur) {
		this.optionChauffeur = optionChauffeur;
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
	

}
