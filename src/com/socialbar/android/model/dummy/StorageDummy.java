package com.socialbar.android.model.dummy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import android.os.Environment;
import android.util.Log;

public class StorageDummy implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<DummyEstablishment> establishments;
	private DummyUser user;

	private transient DummyEstablishment temp;
	private transient final String FILE = "socialbar.txt";

	public StorageDummy() {
		this.checkStorage();
	}

	public DummyEstablishment addEstablishment(DummyEstablishment de) {
		int id = this.establishments.size();
		de.setId(String.valueOf(id));
		this.establishments.add(de);

		Log.i("StorageDummy",
				"adicionando: " + String.valueOf(this.establishments.size()));
		return de;
	}

	public boolean removeEstablishment(DummyEstablishment de) {
		return this.establishments.remove(de);
	}

	public DummyEstablishment getNewEstablishment() {
		this.temp = new DummyEstablishment();
		return this.temp;
	}

	public DummyEstablishment getEstablishmentById(String id) {
		for (DummyEstablishment de : establishments)
			if (de.getID().equals(id))
				return de;
		return null;
	}

	public List<DummyEstablishment> getEstablishmentsByName(String name) {
		List<DummyEstablishment> list = new ArrayList<DummyEstablishment>();
		for (DummyEstablishment de : establishments)
			if (de.getName().toLowerCase().contains(name.toLowerCase()))
				list.add(de);
		return list;
	}

	public List<DummyEstablishment> getEstablishmentsByFavorite() {
		List<DummyEstablishment> list = new ArrayList<DummyEstablishment>();
		for (DummyEstablishment de : establishments)
			if (de.isFavorite())
				list.add(de);
		return list;
	}

	public List<DummyEstablishment> getEstablishmentsByLatLong(double latitude,
			double longitude) {

		List<DummyEstablishment> list = new ArrayList<DummyEstablishment>();
		for (DummyEstablishment de : this.establishments) {
			double range = 10 / 1000.0;
			double lat = de.getLatitude() - latitude;
			double lon = de.getLongitude() - longitude;

			if (lat <= range && lon <= range)
				list.add(de);
		}

		if (list.size() == 0)
			return this.generateEstablishmentByLatLong(latitude, longitude);

		return list;

	}

	public void setUser(DummyUser user) {
		this.user = user;
	}

	public DummyUser getUser() {
		return this.user;
	}

	public void save() {
		if (this.temp != null && this.checkintegrity(this.temp))
			this.addEstablishment(this.temp);
		this.temp = null;
		this.saveStorage();
	}

	private void initialize() {
		this.user = new DummyUser();
		this.user.setName("SocialBar User");

		this.establishments = new ArrayList<DummyEstablishment>();
	}

	/**
	 * Verifica se o arquivo existe
	 */
	private void checkStorage() {
		File file = new File(Environment.getExternalStorageDirectory(), FILE);
		if (file != null && file.exists()) {
			this.loadStorage();
			this.checkIds();
			if (this.establishments == null) {
				this.initialize();
			}
		} else {
			this.initialize();
			this.saveStorage();
		}
	}

	/**
	 * salva objeto em arquivo
	 */
	private void saveStorage() {
		File file;
		ObjectOutputStream os;
		try {
			file = new File(Environment.getExternalStorageDirectory(), FILE);
			os = new ObjectOutputStream(new FileOutputStream(file));
			os.writeObject(this);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * carrega arquivo para objeto
	 */
	private void loadStorage() {
		File file;
		ObjectInputStream is;
		try {
			file = new File(Environment.getExternalStorageDirectory(), FILE);
			is = new ObjectInputStream(new FileInputStream(file));
			StorageDummy storage = (StorageDummy) is.readObject();
			this.user = storage.user;
			this.establishments = storage.establishments;
			is.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("StorageDummy",
				"carregado: " + String.valueOf(this.establishments));

	}

	/**
	 * Checar por ids corrompidas
	 */
	private void checkIds() {
		int i = 0;
		if (this.establishments != null)
			for (DummyEstablishment de : this.establishments) {
				if (de.getID() == null)
					de.setId(String.valueOf(i));
				i++;
			}
	}

	/**
	 * criação de pontos
	 * 
	 */
	public List<DummyEstablishment> generateEstablishmentByLatLong(double lat,
			double lon) {
		List<DummyEstablishment> list = new ArrayList<DummyEstablishment>();
		list.add(this.multiCreate("1", lat + 3 / 1000.0, lon));
		list.add(this.multiCreate("2", lat, lon + 3 / 1000.0));
		list.add(this.multiCreate("3", lat, lon - 3 / 1000.0));
		list.add(this.multiCreate("4", lat - 3 / 1000.0, lon));
		// diagonais
		list.add(this.multiCreate("5", lat + 3 / 1000.0, lon + 3 / 1000.0));// cima-direita
		list.add(this.multiCreate("6", lat + 3 / 1000.0, lon - 3 / 1000.0));// cima-esquerda
		list.add(this.multiCreate("7", lat - 3 / 1000.0, lon + 3 / 1000.0));// baixo-direita
		list.add(this.multiCreate("8", lat - 3 / 1000.0, lon - 3 / 1000.0));// baixo-esquerda

		this.saveStorage();// salva alteracoes para o arquivo
		return list;
	}

	private DummyEstablishment multiCreate(String i, double lat, double lon) {

		DummyEstablishment de = this.create("Bar " + i, i + "1234-5678",
				"Rua multi " + i, lat, lon);
		this.addEstablishment(de);
		return de;
	}

	private DummyEstablishment create(String name, String phone,
			String address, double lat, double lon) {
		DummyEstablishment e = new DummyEstablishment();
		e.setName(name);
		e.setPhoneNumber(phone);
		e.setAddress(address);
		e.setLatitude(lat);
		e.setLongitude(lon);
		e.setLastModified(System.currentTimeMillis());

		return e;
	}
	private boolean checkintegrity(DummyEstablishment de){
		if(de.getName() == null)
			return false;
		if(de.getAddress() == null)
			return false;
		if(de.getPhoneNumber() == null)
			return false;
		if(de.getLongitude() == 0.0)
			return false;
		return true;
	}

}
