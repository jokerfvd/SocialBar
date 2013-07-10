package com.socialbar.android.model.real;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.dummy.DummyFeature;

public class RealFeature extends DummyFeature{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RealFeature(JSONObject json) throws JSONException {
		super(json.getString("nome"));
	}

}
