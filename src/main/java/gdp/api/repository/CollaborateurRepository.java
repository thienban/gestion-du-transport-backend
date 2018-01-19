package gdp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Collaborateur;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {
	Collaborateur findByEmail(String email);

	Collaborateur findByMatricule(String matricule);
}
