package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class DeleteEstabelecimentoRestMethod extends
		AbstractRestMethod<Estabelecimento> {

	private Context mContext;

	private URI ESTABELECIMENTO_URI;

	public DeleteEstabelecimentoRestMethod(Context context, Map<String, List<String>> header) {
		mContext = context.getApplicationContext();
		int id = Integer.parseInt(header.get("ID").get(0));
		ESTABELECIMENTO_URI = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/"
						+ Integer.toString(id) + ".json");
	}

	@Override
	protected Request buildRequest() {
		return new Request(Method.DELETE, ESTABELECIMENTO_URI, null, null);
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
