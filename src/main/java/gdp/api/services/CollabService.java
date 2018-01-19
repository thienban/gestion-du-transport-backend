package gdp.api.services;

import java.util.List;

import gdp.api.entities.Collaborateur;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface CollabService {
	@GET("collaborateurs")
	Observable<Collaborateur> getCollabInfoByMatricule(@Query("matricule") String matricule);

	@GET("collaborateurs")
	Observable<List<Collaborateur>> getCollabInfoByEmail(@Query("email") String email);

	@GET("collaborateurs")
	Observable<List<Collaborateur>> getCollaborateurs(@Query("_limit") Integer limit);
	
}
