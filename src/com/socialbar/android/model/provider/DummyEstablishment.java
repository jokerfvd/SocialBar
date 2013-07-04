package com.socialbar.android.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.Establishment;

public class DummyEstablishment {
	public List<EstablishmentProvider> getList(double lat, double lon){
		List<EstablishmentProvider> list = new ArrayList<EstablishmentProvider>();
		list.add(this.getOne(lat, lon));
		
		EstablishmentProvider e = new EstablishmentProvider("2");
		e.setName("Bar teste2");
		e.setPhoneNumber("1321564131");
		e.setAddress("acesso negado");
		e.setLatitude(lat+0.001);
		e.setLongitude(lon+0.001);
		
		
		list.add(e);
		
		return list;
	}
	public EstablishmentProvider getOne(double lat, double lon){
		EstablishmentProvider e = new EstablishmentProvider("1");
		e.setName("Bar teste");
		e.setPhoneNumber("1321564131");
		e.setAddress("acesso asalsndlaksndlas");
		e.setLatitude(lat);
		e.setLongitude(lon);
		
		return e;
	}
}
