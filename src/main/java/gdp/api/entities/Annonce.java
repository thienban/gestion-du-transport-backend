package gdp.api.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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

	@Column
	private Integer nbPlacesDispos;

	@Enumerated
	private StatusCovoit statusCovoit;

	@ManyToOne
	private Adresse adresseDepart;

	@ManyToOne
	private Adresse adresseArrive;

	@Column
	private Integer dureeTrajet;

	@Column
	private Integer distance;
	
	@Embedded
	private VehiculeCovoit vehicule;

	@ManyToMany
	@JoinTable(name = "RESERVATIONS_COVOIT", joinColumns = @JoinColumn(name = "ANNONCE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PASSAGERS_ID", referencedColumnName = "ID"))
	private List<Collaborateur> passagers = new ArrayList<>();

	public Integer getNbPlacesRestantes() {
		if (passagers == null) {
			return nbPlacesDispos;
		}
		return nbPlacesDispos - passagers.size();
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

	public Integer getNbPlacesDispos() {
		return nbPlacesDispos;
	}

	public void setNbPlacesDispos(Integer nbPlaces) {
		this.nbPlacesDispos = nbPlaces;
	}

	public StatusCovoit getStatusCovoit() {
		return statusCovoit;
	}

	public void setStatusCovoit(StatusCovoit status) {
		this.statusCovoit = status;
	}

	public Adresse getAdresseDepart() {
		return adresseDepart;
	}

	public void setAdresseDepart(Adresse adresseDepart) {
		this.adresseDepart = adresseDepart;
	}

	public Adresse getAdresseArrive() {
		return adresseArrive;
	}

	public void setAdresseArrive(Adresse adresseArrive) {
		this.adresseArrive = adresseArrive;
	}

	public Integer getDureeTrajet() {
		return dureeTrajet;
	}

	public void setDureeTrajet(Integer dureeTrajet) {
		this.dureeTrajet = dureeTrajet;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public List<Collaborateur> getPassagers() {
		return passagers;
	}

	public void setPassagers(List<Collaborateur> passagers) {
		this.passagers = passagers;
	}

	public VehiculeCovoit getVehicule() {
		return vehicule;
	}

	public void setVehicule(VehiculeCovoit vehicule) {
		this.vehicule = vehicule;
	}
}
