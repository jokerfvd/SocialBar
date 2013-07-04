package com.socialbar.android.model.provider;

import java.util.List;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Feature;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.Product;
import com.socialbar.android.model.User;

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
		if (id == null)
			return new EstablishmentProvider();
		return new DummyEstablishment().getOne(0, 0);
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

}
