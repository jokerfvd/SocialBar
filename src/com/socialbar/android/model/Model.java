package com.socialbar.android.model;

import java.util.List;

public interface Model {
	/**
	 * interfaces para todos os objetos - basicas
	 */
	Establishment getEstablishment();
	Establishment getEstablishment(String id);

	List<Establishment> getEstablishment(double latitude, double longitude);

	List<Establishment> getEstablishment(String... args);

	User getUser(String name, String password);

	User getUser();
	
}
