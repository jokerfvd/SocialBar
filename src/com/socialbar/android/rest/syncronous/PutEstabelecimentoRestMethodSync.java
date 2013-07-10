package com.socialbar.android.rest.syncronous;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONStringer;

import android.content.Context;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.rest.rest.PutEstabelecimentoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class PutEstabelecimentoRestMethodSync extends PutEstabelecimentoRestMethod{
	
	public PutEstabelecimentoRestMethodSync(Establishment establishment) {
		JSONStringer json;
		try {
			json = new JSONStringer()
			.object()
			.key("estabelecimento")
			.object()
			.key("nome")
			.value(establishment.getName())
			.key("endereco")
			.value(establishment.getAddress())
			.key("telefone")
			.value(establishment.getPhoneNumber())
			.key("latitude")
			.value(establishment.getLatitude())
			.key("longitude")
			.value(establishment.getLongitude())
			.endObject().endObject();
			mBody = json.toString().getBytes();
			
			ESTABELECIMENTO_URI = URI
					.create("http://restserveruff.herokuapp.com/estabelecimentos/"
							+ establishment.getID() + ".json");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}

}
