package com.socialbar.android.rest.resource;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.widget.Toast;


public class Estabelecimentos implements Resource {

	private ArrayList<Estabelecimento> lista;

	public Estabelecimentos(JSONArray jArray) throws JSONException {
		lista = new ArrayList<Estabelecimento>();		
		for(int i=0; i < jArray.length(); i++){
			Log.d("JSON: ", jArray.getJSONObject(i).toString());
			Estabelecimento estabelecimento = new Estabelecimento(jArray.getJSONObject(i));
		    lista.add(estabelecimento);
		 }
	}

	public ArrayList<Estabelecimento> getLista() {
		return lista;
	}
	

}
