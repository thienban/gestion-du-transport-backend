package gdp.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import gdp.api.entities.Collaborateur;
import gdp.api.entities.Role;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.HttpService;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CollaborateurRepository collaborateurRepository;

	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Autowired
	private HttpService http;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Collaborateur collabExist = collaborateurRepository.findByEmail(email);
		String password;
		String mail;
		Role role;
		if (collabExist != null) {
			password = collabExist.getPassword();
			mail = collabExist.getEmail();
			role = collabExist.getRole();
		} else {
			List<Collaborateur> newCollab = http.getCollabService().getCollabInfoByEmail(email).toBlocking().first();

			if (newCollab.isEmpty()) {
				throw new UsernameNotFoundException(email);
			}
			collaborateurRepository.save(newCollab.get(0));
			password = newCollab.get(0).getPassword();
			mail = newCollab.get(0).getEmail();
			role = newCollab.get(0).getRole();
		}
		User user = new User(mail, password, Arrays.asList(role.getAuthority()));

		return user;

	}

	public boolean verifyUserEmail(String email) {
		Collaborateur collabExist = collaborateurRepository.findByEmail(email);
		if (collabExist != null) {
			return true;
		} else {
			List<Collaborateur> newCollab = http.getCollabService().getCollabInfoByEmail(email).toBlocking().first();
			if (newCollab.isEmpty()) {
				return false;
			} else {
				collaborateurRepository.save(newCollab.get(0));
				return true;
			}
		}
	}
}

