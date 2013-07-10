package com.socialbar.android.model.real;

import java.util.List;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.model.User;
import com.socialbar.android.model.dummy.FactoryDummy;
import com.socialbar.android.model.dummy.StorageDummy;
import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestMethodResult;
import com.socialbar.android.rest.syncronous.GetEstabelecimentoRestMethodSync;
import com.socialbar.android.rest.syncronous.GetEstabelecimentosRestMethodSync;
import com.socialbar.android.rest.syncronous.GetFavoritosRestMethodSync;
import com.socialbar.android.rest.syncronous.GetUsuarioRestMethodSync;

import android.content.BroadcastReceiver;
import android.content.Context;

public class RealModel implements Model{

	private static RealModel instance;

	public RealModel() {
	}
	
	
	public static Model getInstance() {
		if (instance == null)
			instance = new RealModel();
		return instance;
	}
	
	@Override
	public Establishment getEstablishment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Establishment getEstablishment(String id) {
		GetEstabelecimentoRestMethodSync rest = new GetEstabelecimentoRestMethodSync(id);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);		
		RealEstablishment establishment = rest.buildResult1(response);
		return establishment;
	}

	@Override
	public List<RealEstablishment> getEstablishment(double latitude,
			double longitude) {
		GetEstabelecimentosRestMethodSync rest = new GetEstabelecimentosRestMethodSync(latitude,longitude);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);
		List<RealEstablishment> establishments = rest.buildResult1(response);
		return establishments;
	}

	@Override
	public List<RealEstablishment> getEstablishment(String... args) {
		GetEstabelecimentosRestMethodSync rest = new GetEstabelecimentosRestMethodSync(args);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);
		List<RealEstablishment> establishments = rest.buildResult1(response);
		return establishments;
	}

	@Override
	public User getUser(String login, String password) {
		GetUsuarioRestMethodSync rest = new GetUsuarioRestMethodSync(login,password);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);
		RealUser user = rest.buildResult1(response);
		return user;
	}

	@Override
	//que usu�rio ?
	public User getUser() {
		return getUser("jokerfvd","a");
	}

	@Override
	//favoritos de quem ?
	//esta setado para o primeiro usu�rio de id 132, mas n�o � pra ser assim
	public List<RealEstablishment> getFavorites() {
		User user = getUser();
		GetFavoritosRestMethodSync rest = new GetFavoritosRestMethodSync("22");
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);
		List<RealEstablishment> establishments = rest.buildResult1(response);
		this.setFavoriteEstablishment(establishments);
		return establishments;
	}

	@Override
	public BroadcastReceiver getEstablishmentPrototype(ModelEvent me, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		
	}
	private void setFavoriteEstablishment(List<RealEstablishment> es){
		for (Establishment e : es) 
			e.setFavorite(true);
	}
	private Establishment setFavorite(String id){
		return null;
	}

}