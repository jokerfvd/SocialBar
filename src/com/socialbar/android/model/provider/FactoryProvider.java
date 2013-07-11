package com.socialbar.android.model.provider;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.socialbar.android.activities.DummyVerEstabelecimentoActivity.MyReceiver;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Feature;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.model.Product;
import com.socialbar.android.model.User;
import com.socialbar.android.rest.service.ServiceHelper;

public class FactoryProvider implements Model {
	private static FactoryProvider instance;

	private FactoryProvider() {
		instance = this;
	}

	public static Model getInstance() {
		if (instance == null)
			new FactoryProvider();
		return instance;
	}
	
	private <T extends Establishment> List<T> getEstablishmentObject(double latitude, double longitude) {
		return (List<T>) new DummyEstablishment().getList(latitude, longitude);
	}
	
	@Override
	public List<Establishment> getEstablishment(double longitude,double latitude) {
		return this.getEstablishmentObject(latitude, longitude);
	}

	@Override
	public Establishment getEstablishment(String id) {
		return new DummyEstablishment().getById(id, 0, 0);
	}

	@Override
	public List<Establishment> getEstablishment(String... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Establishment getEstablishment() {
			return new EstablishmentProvider();
	}

	@Override
	public List<Establishment> getFavorites() {
		return this.getEstablishmentObject(0, 0);
	}

	@Override
	public BroadcastReceiver getEstablishmentPrototype(ModelEvent me, String id) {
		ProviderActivity pa = new ProviderActivity(me);
		return pa.getEstablishment(id);
	}

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addEstablishment(Establishment establishment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEstablishment(Establishment establishment) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setEstablishmentFavorite(Establishment establishment) {
		// TODO Auto-generated method stub
		
	}

}
