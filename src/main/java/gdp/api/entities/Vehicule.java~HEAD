package gdp.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="VEHICULE")
public class Vehicule {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="IMMATRICULATION")
	private String immatriculation;
	
	@Column(name="NB_PLACES")
	private Integer nbPlaces;
	
	@Column(name="PHOTO")
	private String photo;
	
	@Column(name="STATUS")
	private StatusVehicule status;
	
	@ManyToOne()
	@JoinColumn(name="ID_CATEGORIE")
	private Categorie categorie;
	
	@ManyToOne()
	@JoinColumn(name="ID_MARQUE")
	private Marque marque;

	public Vehicule() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public StatusVehicule getStatus() {
		return status;
	}

	public void setStatus(StatusVehicule status) {
		this.status = status;
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
