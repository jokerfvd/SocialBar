package com.socialbar.android.rest.syncronous;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.socialbar.android.model.real.RealEstablishment;
import com.socialbar.android.rest.rest.GetFavoritosRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class GetFavoritosRestMethodSync extends GetFavoritosRestMethod {
	
	public GetFavoritosRestMethodSync(String id) {
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ id + "/favoritos.json");
	}
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
	
	public List<RealEstablishment> buildResult1(Response response) {
		List<RealEstablishment> establishments = new ArrayList<RealEstablishment>();
		String responseBody = new String(response.body);
		JSONArray estabelecimentos;
		try {
			estabelecimentos = new JSONArray(responseBody);
			for(int i=0;i<estabelecimentos.length();i++){
				RealEstablishment establishment = new RealEstablishment(estabelecimentos.getJSONObject(i));
				establishments.add(establishment);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return establishments;
	}
}
