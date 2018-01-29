package gdp.api.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class VehiculeCovoit {
	
	@Column
	private String immatriculation;
	
	@Column
	private Integer nbPlaces;
	
	@Column
	private String marque;
	
	@Column
	private String modele;

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

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}
	
}
