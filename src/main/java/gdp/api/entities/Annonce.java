package gdp.api.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;

@Entity
public class Annonce {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Collaborateur auteur;

	@Column
	private LocalDateTime dateDepart;

	@Column
	private LocalDateTime dateArrivee;


	@Enumerated
	private StatusCovoit status;

	@Column
	private String adresseDepart;

	@Column
	private String adresseArrive;

	@Column
	private Duration dureeTrajet;
	
	@Column
	@JsonProperty(access=Access.READ_ONLY)
	private Integer nbPlacesRestantes;
	
	@Column
	private Distance distance;

	@ManyToMany
	@JoinTable(name = "RESERVATIONS_COVOIT", joinColumns = @JoinColumn(name = "ANNONCE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PASSAGERS_ID", referencedColumnName = "ID"))
	private List<Collaborateur> passagers = new ArrayList<>();

	public Integer getNbPlacesRestantes() {
		if (passagers == null) {
			return nbPlaces;
		}
		return nbPlaces - passagers.size();
	}
	public void setNbPlacesRestantes() {
		nbPlacesRestantes = getNbPlacesRestantes();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Collaborateur getAuteur() {
		return auteur;
	}

	public void setAuteur(Collaborateur auteur) {
		this.auteur = auteur;
	}

	public LocalDateTime getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(LocalDateTime dateDepart) {
		this.dateDepart = dateDepart;
	}

	public LocalDateTime getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(LocalDateTime dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public Integer getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(Integer nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	public StatusCovoit getStatus() {
		return status;
	}

	public void setStatus(StatusCovoit status) {
		this.status = status;
	}

	public Duration getDureeTrajet() {
		return dureeTrajet;
	}

	public void setDureeTrajet(Duration duration) {
		this.dureeTrajet = duration;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance2) {
		this.distance = distance2;
	}

	public List<Collaborateur> getPassagers() {
		return passagers;
	}

	public void setPassagers(List<Collaborateur> passagers) {
		this.passagers = passagers;
	}

	public String getAdresseDepart() {
		return adresseDepart;
	}

	public void setAdresseDepart(String adresseDepart) {
		this.adresseDepart = adresseDepart;
	}

	public String getAdresseArrive() {
		return adresseArrive;
	}

	public void setAdresseArrive(String adresseArrive) {
		this.adresseArrive = adresseArrive;
	}
}
