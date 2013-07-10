package com.socialbar.android.model.real;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.dummy.DummyProduct;

public class RealProduct extends DummyProduct{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RealProduct(JSONObject json) throws JSONException {	
		super(null, 0);
		String valor = json.getString("valor");
		//String type = json.getString("tipo");
		JSONObject product = json.getJSONObject("produto");
		String name = product.getString("nome");
		double price = Double.parseDouble(valor.substring(2).replace(",", "."));
		setName(name);
		setPrice(price);
		//setType(type);
	}

}
