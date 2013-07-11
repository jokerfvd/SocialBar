package com.socialbar.android.rest.syncronous;

import java.net.URI;

import com.socialbar.android.rest.rest.PostFavoritoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class PostFavoritoRestMethodSync extends PostFavoritoRestMethod{

	public PostFavoritoRestMethodSync(String login, byte[] body) {
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ login + "/favoritos.json");
		mBody = body;
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
}
