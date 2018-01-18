package gdp.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import gdp.api.entities.Collaborateur; 
import gdp.api.repository.CollaborateurRepository; 
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private CollaborateurRepository collaborateurRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Collaborateur account = collaborateurRepository.findByEmail(email);
		if (account == null) {
			throw new UsernameNotFoundException(email);
		}
		return new User(account.getEmail(), account.getPassword(), emptyList());
	}
}
