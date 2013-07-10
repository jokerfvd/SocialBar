package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;

import com.socialbar.android.rest.resource.Estabelecimentos;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class GetFavoritosRestMethod extends AbstractRestMethod<Estabelecimentos> {
	
	private Context mContext;

	protected URI FAVORITOS_URI;
	
	public GetFavoritosRestMethod(Context context) {
		mContext = context.getApplicationContext();
	}
	
	public GetFavoritosRestMethod(Context context, Map<String, List<String>> header) {
		mContext = context.getApplicationContext();
		int id = Integer.parseInt(header.get("ID").get(0));
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ Integer.toString(id) + "/favoritos.json");
	}
	
	public GetFavoritosRestMethod() {
	}

	@Override
	protected Request buildRequest() {

		return new Request(Method.GET, FAVORITOS_URI, null, null);
	}
	
	@Override
	protected RestMethodResult<Estabelecimentos> buildResult(Response response) {

		int status = response.status;
		String statusMsg = "";
		String responseBody = new String(response.body);
		Estabelecimentos resource = null;
		try {
			resource = new Estabelecimentos(new JSONArray(responseBody));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return new RestMethodResult<Estabelecimentos>(status, statusMsg, resource);
	}

	@Override
	protected Estabelecimentos parseResponseBody(String responseBody) throws Exception {
		JSONArray jArray = new JSONArray(new String(responseBody));
		return new Estabelecimentos(jArray);
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

}
