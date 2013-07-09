package com.socialbar.android.model.dummy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.*;

public class DummyEstablishment implements Establishment,Serializable {
	private static final long serialVersionUID = 1L;
	private String Id;
	private double latitude;
	private double longitude;
	private String phone;
	private String name;
	private String address;
	private String description;
	private int people;
	private long lastModified;
	private long createdAt;
	private boolean favorite;
	private List<Product> products;
	private List<Feature> features;

	public DummyEstablishment() {
		this.createdAt = System.currentTimeMillis();
	}

	public DummyEstablishment(String id) {
		this.Id = id;
	}

	public void setId(String id) {
		this.Id = id;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public int getPeople() {
		return (int)(Math.random()*10000.0);
	}

	@Override
	public String getAddress() {
		return this.address;
	}

	@Override
	public String getPhoneNumber() {
		return this.phone;
	}

	@Override
	public <T extends Feature>List<T> getFeatures() {
		return (List<T>) this.features;
	}

	@Override
	public <T extends Product>List<T> getProducts() {
		return (List<T>) this.products;
	}
	
	@Override
	public byte[] getIconImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getWelcomeImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLikeIt(Object user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeLikeIt(Object user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addComment(Object user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeComment(Object user) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkIn() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setName(String name) {
		this.name = name;

	}

	@Override
	public void setAddress(String address) {
		this.address = address;

	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phone = phoneNumber;

	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;

	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;

	}

	@Override
	public boolean addProduct(Product product) {
		if(this.products == null)
			this.products = new ArrayList<Product>();		
		return this.products.add(product);
	}

	@Override
	public boolean removeProduct(Product product) {	
		if(this.products == null)
			return false;
		return this.products.remove(product);
	}

	@Override
	public boolean addFeature(Feature feature) {
		if(this.features == null)
			this.features = new ArrayList<Feature>();
		return this.features.add(feature);
	}

	@Override
	public boolean removeFeature(Feature feature) {
		if(this.features == null)
			return false;
		return this.features.remove(feature);
	}

	@Override
	public String getID() {
		return this.Id;
	}

	@Override
	public long getLastModified() {
		return this.lastModified;
	}

	@Override
	public void setLastModified(long miliseconds) {
		this.lastModified = miliseconds;

	}

	@Override
	public boolean isFavorite() {
		return this.favorite;
	}

	@Override
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;

	}

	@Override
	public long getCreatedAt() {
		
		return this.createdAt;
	}

}
