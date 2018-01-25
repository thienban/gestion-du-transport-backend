package gdp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Marque;
import gdp.api.entities.Modele;

public interface ModeleRepository extends JpaRepository<Modele, Integer>{

}
