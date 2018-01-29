package gdp.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gdp.api.entities.Collaborateur;
import gdp.api.entities.Role;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Integer> {
	Collaborateur findByEmail(String email);

	Collaborateur findByMatricule(String matricule);
	
	List<Collaborateur> findByRole(Role role);
}
