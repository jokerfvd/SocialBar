package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.resource.Favorito;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class PostFavoritoRestMethod extends
		AbstractRestMethod<Favorito> {

	private Context mContext;
	public byte[] mBody;

	public URI FAVORITOS_URI;

	public PostFavoritoRestMethod(){
		
	}
	
	public PostFavoritoRestMethod(Context context, Map<String, List<String>> header,  byte[] body) {
		mContext = context.getApplicationContext();
		int id = Integer.parseInt(header.get("ID").get(0));
		FAVORITOS_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ Integer.toString(id) + "/favoritos.json");
	}

	public byte[] getBody() {
		return mBody;
	}

	@Override
	protected Request buildRequest() {
		Request request = new Request(Method.POST, FAVORITOS_URI, null, mBody);
		request.addHeader("Content-Type", Arrays.asList("application/json"));
		return request;
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
