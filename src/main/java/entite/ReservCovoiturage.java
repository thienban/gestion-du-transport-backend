package entite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name="RESERV_COVOITURAGE")
public class ReservCovoiturage extends ReservationTransport{

	
	@Column(name="NB_PLACES_DISPOS")
	private Integer nbPlacesDispos;
	
	@Enumerated
	private StatusCovoit status;
	
	@Column(name="ADRESSE_DEPART")
	private Adresse adresseDepart;
	
	@Column(name="ADRESSE_ARRIVE")
	private Adresse adresseArrive;
	
	@Column(name="DUREE_TRAJET")
	private Integer dureeTrajet;
	
	@Column(name="DISTANCE")
	private Integer distance;
	
	public ReservCovoiturage() {
		
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
