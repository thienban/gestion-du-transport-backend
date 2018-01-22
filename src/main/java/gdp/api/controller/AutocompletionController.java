package gdp.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdp.api.entities.Annonce;
import gdp.api.entities.Collaborateur;
import gdp.api.repository.AnnonceRepository;
import gdp.api.repository.CollaborateurRepository;
import gdp.api.services.GoogleApiService;

@RestController
@RequestMapping("autocomplete")
public class AutocompletionController {
	@Autowired GoogleApiService googleApiSvc;


	@GetMapping(path = "/{input}")
	public List<String> getPredictions(@PathVariable String input) {
		return googleApiSvc.autocompleteAdress(input);
	}


}
