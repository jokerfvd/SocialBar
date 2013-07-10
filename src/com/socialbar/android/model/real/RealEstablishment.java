package com.socialbar.android.model.real;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.dummy.DummyEstablishment;

public class RealEstablishment extends DummyEstablishment{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RealEstablishment(){
		super();
	}
	
	
	public RealEstablishment(JSONObject json) throws JSONException {	
		setName(json.getString("nome"));
		setAddress(json.getString("endereco"));
		setPhoneNumber(json.getString("telefone"));
		//this.gostei = Integer.parseInt(json.getString("gostei"));
		setLatitude(Double.parseDouble(json.getString("latitude")));
		setLongitude(Double.parseDouble(json.getString("longitude")));
		setId(json.getString("id"));
		JSONArray precos = json.getJSONArray("precos");
		for(int i=0; i<precos.length();i++){
			JSONObject preco = precos.getJSONObject(i);
			RealProduct product = new RealProduct(preco);
			addProduct(product);
		}
		JSONArray caracteristicas = json.getJSONArray("caracteristicas");
		for(int i=0; i<caracteristicas.length();i++){
			JSONObject caracteristica = caracteristicas.getJSONObject(i);
			RealFeature feature = new RealFeature(caracteristica);
			addFeature(feature);
		}
	}
	

}
