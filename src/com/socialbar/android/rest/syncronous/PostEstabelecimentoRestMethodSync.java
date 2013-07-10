package com.socialbar.android.rest.syncronous;

import org.json.JSONException;
import org.json.JSONStringer;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.real.RealEstablishment;
import com.socialbar.android.rest.rest.PostEstabelecimentoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;

public class PostEstabelecimentoRestMethodSync extends PostEstabelecimentoRestMethod {
	
	public PostEstabelecimentoRestMethodSync(Establishment establishment) {
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
