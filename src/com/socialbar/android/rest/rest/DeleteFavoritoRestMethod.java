package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.resource.Favorito;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class DeleteFavoritoRestMethod extends
		AbstractRestMethod<Favorito> {

	private Context mContext;

	public URI FAVORITOS_URI;

	public DeleteFavoritoRestMethod(){
		
	}
	
	public DeleteFavoritoRestMethod(Context context, Map<String, List<String>> header) {
		mContext = context.getApplicationContext();
		int id = Integer.parseInt(header.get("ID").get(0));
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ Integer.toString(id) + "/favoritos.json");
	}

	@Override
	protected Request buildRequest() {
		return new Request(Method.DELETE, FAVORITOS_URI, null, null);
	}

	@Override
	protected Favorito parseResponseBody(String responseBody)
			throws Exception {
		JSONObject json = new JSONObject(new String(responseBody));
		return new Favorito(json);
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

}
