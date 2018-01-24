package gdp.api.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
	public List<Annonce> findByAuteur(Collaborateur collab);

	@Query(value = "SELECT * FROM (SELECT adresse_depart AS adresse FROM annonce UNION SELECT adresse_arrive FROM annonce) WHERE LOWER(adresse) LIKE LOWER('%' || ?1 || '%')", nativeQuery = true)
	public List<String> selectAllAdresses(String input);

	@Query("SELECT a FROM Annonce a WHERE ?1 NOT IN a.passagers AND ?1 != a.auteur AND a.dateDepart < CURRENT_TIMESTAMP")
	List<Annonce> findAvailableReservations(Collaborateur pour);

	List<Annonce> findByDateDepartGreaterThan(LocalDateTime date);

	List<Annonce> findByDateDepartGreaterThanAndAuteurIsNot(LocalDateTime date, Collaborateur notauteur);

}
