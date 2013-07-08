package com.socialbar.android.model;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;

public interface Model {
	/**
	 * interfaces para todos os objetos - basicas
	 */
	Establishment getEstablishment();
	Establishment getEstablishment(String id);

	<T extends Establishment>List<T> getEstablishment(double latitude, double longitude);

	<T extends Establishment>List<T> getEstablishment(String... args);

	User getUser(String name, String password);

	User getUser();
	
	<T extends Establishment>List<T> getFavorites();
	
	BroadcastReceiver getEstablishmentPrototype(ModelEvent me, String id);
	
	void setContext(Context context);
	
}
