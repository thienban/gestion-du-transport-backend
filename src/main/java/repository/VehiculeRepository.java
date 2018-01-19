package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entite.ReservCovoiturage;

public interface VehiculeRepository extends JpaRepository<ReservCovoiturage, Integer>{

}
