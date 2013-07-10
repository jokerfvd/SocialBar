package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.Arrays;
import org.json.JSONObject;

import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class PostEstabelecimentoRestMethod extends
		AbstractRestMethod<Estabelecimento> {

	private Context mContext;
	public byte[] mBody;

	private static final URI ESTABELECIMENTO_URI = URI.create("http://restserveruff.herokuapp.com/estabelecimentos");

	public PostEstabelecimentoRestMethod(){
		
	}
	
	public PostEstabelecimentoRestMethod(Context context,  byte[] body) {
		mContext = context.getApplicationContext();
		mBody = body;
	}

	public byte[] getBody() {
		return mBody;
	}

	@Override
	protected Request buildRequest() {
		Request request = new Request(Method.POST, ESTABELECIMENTO_URI, null, mBody);
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
