package gdp.api.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	ADMIN, COLLABORATEUR, CHAUFFEUR;
	
	public GrantedAuthority getAuthority() {
		return new SimpleGrantedAuthority(this.name());
	}
}
