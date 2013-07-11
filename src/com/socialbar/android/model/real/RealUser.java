package com.socialbar.android.model.real;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.dummy.DummyUser;

public class RealUser extends DummyUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public RealUser(String login, String token) {
		setLogin(login);
		setToken(token);
	}



	public RealUser(JSONObject json) throws JSONException {	
		setName(json.getString("nome"));
		setPassword(json.getString("senha"));
		setLogin(json.getString("login"));
	}
}
