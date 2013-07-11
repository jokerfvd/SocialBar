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
import com.socialbar.android.rest.rest.RestMethod;
import com.socialbar.android.rest.rest.RestMethodResult;
import com.socialbar.android.rest.syncronous.DeleteFavoritoRestMethodSync;
import com.socialbar.android.rest.syncronous.GetEstabelecimentoRestMethodSync;
import com.socialbar.android.rest.syncronous.GetEstabelecimentosRestMethodSync;
import com.socialbar.android.rest.syncronous.GetFavoritosRestMethodSync;
import com.socialbar.android.rest.syncronous.GetUsuarioRestMethodSync;
import com.socialbar.android.rest.syncronous.PostEstabelecimentoRestMethodSync;
import com.socialbar.android.rest.syncronous.PostFavoritoRestMethodSync;
import com.socialbar.android.rest.syncronous.PutEstabelecimentoRestMethodSync;

import android.content.BroadcastReceiver;
import android.content.Context;

public class RealModel implements Model{

	private static RealModel instance;
	private RealUser user;

	public RealModel() {
		this.user = new RealUser("jokerfvd","duvaluser");
	}
	
	
	public static Model getInstance() {
		if (instance == null)
			instance = new RealModel();
		return instance;
	}
	
	@Override
	public Establishment getEstablishment() {		
		return new RealEstablishment();
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
		GetEstabelecimentosRestMethodSync rest = new GetEstabelecimentosRestMethodSync(latitude,longitude,user.getToken());
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
	//que usuário ?
	public User getUser() {
		return getUser("jokerfvd","a");
	}

	@Override
	//favoritos de quem ?
	//esta setado para o primeiro usuário de id 132, mas não é pra ser assim
	public List<RealEstablishment> getFavorites() {
		GetFavoritosRestMethodSync rest = new GetFavoritosRestMethodSync(user.getLogin());
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);
		List<RealEstablishment> establishments = rest.buildResult1(response);
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

	@Override
	public void updateEstablishment(Establishment establishment) {
		PutEstabelecimentoRestMethodSync rest = new PutEstabelecimentoRestMethodSync(establishment);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);		
	}

	@Override
	public void addEstablishment(Establishment establishment) {
		PostEstabelecimentoRestMethodSync rest = new PostEstabelecimentoRestMethodSync(establishment);
		Request request = rest.buildRequest();
		Response response = rest.doRequest(request);		
	}
	@Override
	public void setEstablishmentFavorite(Establishment establishment) {
		if (establishment.isFavorite()){
			PostFavoritoRestMethodSync rest = new PostFavoritoRestMethodSync(user.getToken() ,user.getLogin(), establishment.getID());
			Request request = rest.buildRequest();
			Response response = rest.doRequest(request);
		}
		else{
			DeleteFavoritoRestMethodSync rest = new DeleteFavoritoRestMethodSync(user.getToken(), user.getLogin(), establishment.getID());
			Request request = rest.buildRequest();
			Response response = rest.doRequest(request);
		}		
	}



}
