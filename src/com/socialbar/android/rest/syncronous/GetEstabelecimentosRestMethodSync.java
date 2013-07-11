package com.socialbar.android.rest.syncronous;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.model.real.RealEstablishment;
import com.socialbar.android.rest.resource.Estabelecimentos;
import com.socialbar.android.rest.rest.GetEstabelecimentosRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestMethodResult;

public class GetEstabelecimentosRestMethodSync extends GetEstabelecimentosRestMethod{

	public GetEstabelecimentosRestMethodSync(double latitude, double longitude, String token) {
		ESTABELECIMENTO_URI = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/proximos.json?"
						+"latitude="+Double.toString(latitude)+ 
						"&longitude="+Double.toString(longitude)+
						"&token="+token);
	}
	
	public GetEstabelecimentosRestMethodSync(String... args) {
		if(args.length > 0){
			String tipo = args[0];
			String arg1 = args[1];
			String arg2 = "";
			if (args.length > 3)
				arg2 = "&token="+args[2];
				ESTABELECIMENTO_URI = URI
					.create("http://restserveruff.herokuapp.com/estabelecimentos/busca.json?"
							+"tipo="+tipo+"&"+tipo+"="+arg1+arg2);
			
		}
	}
	
	public Request buildRequest() {
		return super.buildRequest();
	}
	
	public Response doRequest(Request request) {
		return super.doRequest(request);
	}
	
	public List<RealEstablishment> buildResult1(Response response) {
		List<RealEstablishment> establishments = new ArrayList<RealEstablishment>();
		String responseBody = new String(response.body);
		JSONArray estabelecimentos;
		try {
			estabelecimentos = new JSONArray(responseBody);
			for(int i=0;i<estabelecimentos.length();i++){
				RealEstablishment establishment = new RealEstablishment(estabelecimentos.getJSONObject(i));
				establishments.add(establishment);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return establishments;
	}

}
