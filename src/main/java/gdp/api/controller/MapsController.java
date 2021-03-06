package gdp.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.errors.ApiException;

import gdp.api.entities.TrajetInfo;
import gdp.api.repository.AnnonceRepository;
import gdp.api.services.GoogleApiService;

@RestController
@RequestMapping("maps")
public class MapsController {
	@Autowired
	GoogleApiService googleApiSvc;

	@Autowired
	AnnonceRepository annonceRepo;

	@GetMapping(path = "/autocomplete/{input}")
	public List<String> getPredictions(@PathVariable String input) {
		/*
		 * List<String> knownAdresses = annonceRepo.selectAllAdresses(input).stream()
		 * .map(adresse -> adresse.replaceAll("\\d",
		 * "").trim()).distinct().collect(Collectors.toList());
		 */
		List<String> googleAdresses = googleApiSvc.autocompleteAdress(input);
		// knownAdresses.addAll(googleAdresses);
		return googleAdresses;
	}

	@GetMapping(value = "/directions")
	public TrajetInfo getTrajetInfo(@RequestParam("origin") String origin,
			@RequestParam("destination") String destination) throws ApiException, InterruptedException, IOException {
		return googleApiSvc.getTrajetInfo(origin, destination);
	}

}
