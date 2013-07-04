package com.socialbar.android.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.Establishment;

public class DummyEstablishment {
	public List<EstablishmentProvider> getList(double lat, double lon) {
		return this.multiCreate(10, lat, lon) ;
	}

	public EstablishmentProvider getOne(double lat, double lon) {
		return this
				.create("1", "bar unico", "1234-5678", "Rua unica", lat, lon);
	}
	public EstablishmentProvider getById(String id,double lat, double lon){
		List<EstablishmentProvider> list = this.multiCreate(Integer.valueOf(id), lat, lon);
		return list.get(Integer.valueOf(id) - 1);		
	}
	private List<EstablishmentProvider> multiCreate(int qt, double lat,
			double lon) {
		List<EstablishmentProvider> list = new ArrayList<EstablishmentProvider>();

		for (int i = 1; i <= qt; i++)
			list.add(this.create(String.valueOf(i), "Bar " + String.valueOf(i),
					String.valueOf(i) + "1234-5678",
					"Rua multi " + String.valueOf(i), lat+i/50, lon+i/50));

		return list;
	}

	private EstablishmentProvider create(String id, String name, String phone,
			String address, double lat, double lon) {
		EstablishmentProvider e = new EstablishmentProvider(id);
		e.setName(name);
		e.setPhoneNumber(phone);
		e.setAddress(address);
		e.setLatitude(lat);
		e.setLongitude(lon);

		return e;
	}
}
