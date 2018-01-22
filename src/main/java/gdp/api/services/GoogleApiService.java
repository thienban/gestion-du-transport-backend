package gdp.api.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import gdp.api.entities.TrajetInfo;

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

}
