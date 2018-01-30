package gdp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Modele;

public interface ModeleRepository extends JpaRepository<Modele, Integer>{

	public Modele findByLibelle(String libelle);

	
}
