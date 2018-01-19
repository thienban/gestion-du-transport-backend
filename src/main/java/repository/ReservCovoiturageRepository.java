package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import entite.ReservCovoiturage;

public interface ReservCovoiturageRepository extends JpaRepository<ReservCovoiturage, Integer>{
	List<Annonce> findByAdresseDepartAndAdresseArriveeAndDateDepart (String AdresseDepart, String AdresseArivee, LocalDateTime DateDepart);
}
