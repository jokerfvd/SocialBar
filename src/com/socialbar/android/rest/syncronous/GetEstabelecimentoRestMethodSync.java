package com.socialbar.android.rest.syncronous;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.real.RealEstablishment;
import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.GetEstabelecimentoRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestMethodResult;

public class GetEstabelecimentoRestMethodSync extends GetEstabelecimentoRestMethod {

	public GetEstabelecimentoRestMethodSync(String id) {
		ESTABELECIMENTO_URI = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/"
						+ id + ".json");
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	
	public RealEstablishment buildResult1(Response response) {
		String responseBody = new String(response.body);
		RealEstablishment establishment = null;
		try {
			establishment = new RealEstablishment(new JSONObject(responseBody));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return establishment;
		
		
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}

}
