package entite;

import java.text.Format;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RESERVATION_TRANSPORT")
public abstract class ReservationTransport {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="DATE-DEPART")
	private LocalDate dateDepart;
	
	@Column(name="HEURE_DEPART")
	private LocalTime heureDepart;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getdateDepart() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return dateDepart.format(formatter);
	}

	public void setdateDepart(LocalDate dateDepart) {
		this.dateDepart = dateDepart;
	}

	public String getHeureDepart() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		
		return heureDepart.format(formatter);
	}

	public void setHeureDepart(LocalTime heureDepart) {
		this.heureDepart = heureDepart;
	}
	
}
