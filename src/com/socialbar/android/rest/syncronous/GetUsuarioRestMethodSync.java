package com.socialbar.android.rest.syncronous;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.real.RealEstablishment;
import com.socialbar.android.model.real.RealUser;
import com.socialbar.android.rest.rest.GetUsuarioRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class GetUsuarioRestMethodSync extends GetUsuarioRestMethod{
	
	public GetUsuarioRestMethodSync(String login, String password) {
		USUARIO_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/login.json?"
						+"login="+login+"&pass="+password);
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
	
	public RealUser buildResult1(Response response) {
		String responseBody = new String(response.body);
		RealUser user = null;
		try {
			user = new RealUser(new JSONObject(responseBody));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

}
