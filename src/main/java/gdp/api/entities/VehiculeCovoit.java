package gdp.api.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class VehiculeCovoit {
	
	@Column(name="IMMATRICULATION")
	private String immatriculation;
	
	@Column(name="NB_PLACES")
	private Integer nbPlaces;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="STATUS")
	private StatusVehicule statusVehicule;
	
	@ManyToOne()
	@JoinColumn(name="ID_CATEGORIE")
	private Categorie categorie;
	
	@ManyToOne()
	@JoinColumn(name="ID_MARQUE")
	private Marque marque;

	public VehiculeCovoit() {
		
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public Integer getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(Integer nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public StatusVehicule getStatusVehicule() {
		return statusVehicule;
	}

	public void setStatusVehicule(StatusVehicule status) {
		this.statusVehicule = status;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Marque getMarque() {
		return marque;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}
	
	
}
