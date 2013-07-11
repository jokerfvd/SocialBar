package com.socialbar.android.rest.syncronous;

import java.net.URI;

import com.socialbar.android.rest.rest.PostFavoritoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class PostFavoritoRestMethodSync extends PostFavoritoRestMethod{

	public PostFavoritoRestMethodSync(String token, String login, int establishmentId) {
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ login + "/favoritos.json"
						+"?estabelecimento_id="+Integer.toString(establishmentId)
						+"&token="+token);
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
}
