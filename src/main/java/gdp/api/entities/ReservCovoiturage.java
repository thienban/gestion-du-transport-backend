package gdp.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="RESERV_COVOITURAGE")
public class ReservCovoiturage extends ReservationTransport{

	
	@Column(name="NB_PLACES_DISPOS")
	private Integer nbPlacesDispos;
	
	@Enumerated
	@Column(name="STATUS")
	private StatusCovoit status;
	
	@ManyToOne()
	@JoinColumn(name="ID_ADRESSE_DEPART")
	private Adresse adresseDepart;
	
	@ManyToOne()
	@JoinColumn(name="ID_ADRESSE_ARRIVE")
	private Adresse adresseArrive;
	
	@Column(name="DUREE_TRAJET")
	private Integer dureeTrajet;
	
	@Column(name="DISTANCE")
	private Integer distance;
	
	@ManyToOne()
	@JoinColumn(name="ID_CHAUFFEUR")
	private Collaborateur chauffeur;
	
	public ReservCovoiturage() {
		super();
	}

	public Integer getNbPlacesDispos() {
		return nbPlacesDispos;
	}

	public void setNbPlacesDispos(Integer nbPlacesDispos) {
		this.nbPlacesDispos = nbPlacesDispos;
	}

	public StatusCovoit getStatus() {
		return status;
	}

	public void setStatus(StatusCovoit status) {
		this.status = status;
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
	
}
