package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class PutEstabelecimentoRestMethod extends
		AbstractRestMethod<Estabelecimento> {

	private Context mContext;
	public byte[] mBody;

	public URI ESTABELECIMENTO_URI;

	public PutEstabelecimentoRestMethod(){
		
	}
	
	public PutEstabelecimentoRestMethod(Context context, Map<String, List<String>> header, byte[] body) {
		mContext = context.getApplicationContext();
		mBody = body;
		int id = Integer.parseInt(header.get("ID").get(0));
		ESTABELECIMENTO_URI = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/"
						+ Integer.toString(id) + ".json");
	}

	@Override
	protected Request buildRequest() {
		Request request = new Request(Method.PUT, ESTABELECIMENTO_URI, null, mBody);
		request.addHeader("Content-Type", Arrays.asList("application/json"));
		return request;
	}

	@Override
	protected Estabelecimento parseResponseBody(String responseBody)
			throws Exception {
		JSONObject json = new JSONObject(new String(responseBody));
		return new Estabelecimento(json);
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

}
