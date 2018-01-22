package gdp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;

public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
	public List<Annonce> findByAuteur(Collaborateur collab);
}
