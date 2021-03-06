package com.socialbar.android.rest.rest;

import java.net.URI;


import org.json.JSONArray;
import org.json.JSONException;

import com.socialbar.android.rest.resource.Estabelecimentos;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class GetEstabelecimentosRestMethod extends AbstractRestMethod<Estabelecimentos> {
	
	private Context mContext;

	protected URI ESTABELECIMENTO_URI = URI
			.create("http://restserveruff.herokuapp.com/estabelecimentos.json");
	
	public GetEstabelecimentosRestMethod(Context context) {
		mContext = context.getApplicationContext();
	}
	
	public GetEstabelecimentosRestMethod() {
	}

	@Override
	protected Request buildRequest() {

		return new Request(Method.GET, ESTABELECIMENTO_URI, null, null);
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
