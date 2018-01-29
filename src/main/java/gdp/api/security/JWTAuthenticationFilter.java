package gdp.api.security;

import static gdp.api.security.SecurityConstants.HEADER_STRING;
import static gdp.api.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import gdp.api.entities.EmailPasswordCredential;
import gdp.api.services.TokenService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	private TokenService tokenSvc;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenSvc) {
		this.authenticationManager = authenticationManager;
		this.tokenSvc = tokenSvc;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			EmailPasswordCredential creds = new ObjectMapper().readValue(req.getInputStream(), EmailPasswordCredential.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = tokenSvc.makeToken(auth);
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
