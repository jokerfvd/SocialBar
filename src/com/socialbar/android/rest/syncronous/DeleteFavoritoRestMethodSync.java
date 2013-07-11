package com.socialbar.android.rest.syncronous;

import java.net.URI;

import com.socialbar.android.rest.rest.DeleteFavoritoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class DeleteFavoritoRestMethodSync extends DeleteFavoritoRestMethod{

	public DeleteFavoritoRestMethodSync(String token, String login, String establishmentId) {
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ login + "/favoritos/"+establishmentId+".json"
						+"?token="+token);
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
	
}
