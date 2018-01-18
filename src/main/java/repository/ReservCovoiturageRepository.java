package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entite.ReservCovoiturage;

public interface ReservCovoiturageRepository extends JpaRepository<ReservCovoiturage, Integer>{

}
