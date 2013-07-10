package com.socialbar.android.rest.rest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.rest.resource.Usuario;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.Context;

public class GetUsuarioRestMethod extends
		AbstractRestMethod<Usuario> {

	private Context mContext;

	protected URI USUARIO_URI;

	public GetUsuarioRestMethod() {
	}
	
	public GetUsuarioRestMethod(Context context, Map<String, List<String>> header) {
		mContext = context.getApplicationContext();
		int id = Integer.parseInt(header.get("ID").get(0));
		USUARIO_URI = URI
				.create("http://restserveruff.herokuapp.com/usuarios/"
						+ Integer.toString(id) + ".json");
	}

	@Override
	protected Request buildRequest() {
		return new Request(Method.GET, USUARIO_URI, null, null);
	}

	@Override
	protected RestMethodResult<Usuario> buildResult(Response response) {

		int status = response.status;
		String statusMsg = "";
		String responseBody = new String(response.body);
		Usuario resource = null;
		try {
			resource = new Usuario(new JSONObject(responseBody));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new RestMethodResult<Usuario>(status, statusMsg,
				resource);
	}

	@Override
	protected Usuario parseResponseBody(String responseBody)
			throws Exception {
		JSONObject json = new JSONObject(new String(responseBody));
		return new Usuario(json);
	}

	@Override
	protected Context getContext() {
		return mContext;
	}

}
