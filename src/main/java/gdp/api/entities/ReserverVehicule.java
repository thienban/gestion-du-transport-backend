package gdp.api.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "ReserverVehicule")
public class ReserverVehicule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Collaborateur passager;
	
	@Column
	private Collaborateur chauffeur;
	
	@Column
	private VehiculeSociete vehicule;
	
	@Column
	private boolean optionChauffeur;
	
	@Column
	private boolean disponible;
	
	@Column(nullable=false)
	private LocalDateTime dateReservation;
	

	@Column(nullable=false)
	private LocalDateTime dateRetour;
	

}
