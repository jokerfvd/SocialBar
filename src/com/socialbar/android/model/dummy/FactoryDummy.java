package com.socialbar.android.model.dummy;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;

import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.model.User;

/**
 * Class <code>Fabrica/Modelo de itens posticos</code>.
 */
public class FactoryDummy implements Model {
	private StorageDummy storage;
	private static FactoryDummy instance;

	public FactoryDummy() {
		this.storage = new StorageDummy();
	}
	
	
	public static Model getInstance() {
		if (instance == null)
			instance = new FactoryDummy();
		return instance;
	}
	
	public void save(){
		this.storage.save();
	}

	@Override
	public Establishment getEstablishment() {
		return this.storage.getNewEstablishment();
	}

	@Override
	public Establishment getEstablishment(String id) {		
		return this.storage.getEstablishmentById(id);
	}

	@Override
	public List<DummyEstablishment> getEstablishment(double latitude,double longitude) {
		return this.storage.getEstablishmentsByLatLong(latitude, longitude);
	}

	@Override
	public List<DummyEstablishment> getEstablishment(String... args) {
		if(args.length > 0)
			return this.storage.getEstablishmentsByName(args[0]);
		return new ArrayList<DummyEstablishment>();
	}

	@Override
	public User getUser(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser() {
		return this.storage.getUser();
	}

	@Override
	public List<DummyEstablishment> getFavorites() {		
		return this.storage.getEstablishmentsByFavorite();
	}

	@Override
	public BroadcastReceiver getEstablishmentPrototype(ModelEvent me, String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setContext(Context context) {	
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
