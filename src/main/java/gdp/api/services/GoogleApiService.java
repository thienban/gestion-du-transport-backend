package gdp.api.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import gdp.api.entities.Annonce;
import gdp.api.entities.TrajetInfo;
import io.jsonwebtoken.lang.Collections;

@Service
public class GoogleApiService {

	@Autowired
	GeoApiContext geoApiContext;

	public DirectionsResult getDirections(String origin, String destination)
			throws ApiException, InterruptedException, IOException {
		return DirectionsApi.getDirections(geoApiContext, origin, destination).await();
	}

	public TrajetInfo getTrajetInfo(String origin, String destination)
			throws ApiException, InterruptedException, IOException {
		TrajetInfo info = new TrajetInfo();
		DirectionsResult resDir = getDirections(origin, destination);
		DirectionsRoute route = resDir.routes[resDir.routes.length - 1];
		DirectionsLeg leg = route.legs[route.legs.length - 1];
		info.setDistance(leg.distance);
		info.setDuration(leg.duration);
		return info;
	}

	public Annonce populateTrajetInfo(Annonce annonce) {
		try {
			TrajetInfo info = getTrajetInfo(annonce.getAdresseDepartString(), annonce.getAdresseArriveString());
			annonce.setDateArrivee(annonce.getDateDepart().plusSeconds((info.getDuration().inSeconds)));
			annonce.setDistance(info.getDistance());
			annonce.setDureeTrajet(info.getDuration());
		} catch (ApiException | InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return annonce;
		}
	}
	
	
	public List<String> autocompleteAdress(String adresseStr){
		try {
			AutocompletePrediction[] predictions = PlacesApi.placeAutocomplete(geoApiContext, adresseStr).await() ;
			List<AutocompletePrediction> listPredictions = Collections.arrayToList(predictions);
			return listPredictions.stream().map(p -> p.description).collect(Collectors.toList());
			
			
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
