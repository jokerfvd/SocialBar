package com.socialbar.android.model.provider;

import java.util.List;

import com.socialbar.android.model.*;

public class EstablishmentProvider implements Establishment {
	private String Id;
	private double latitude;
	private double longitude;
	private String phone;
	private String name;
	private String address;
	private String description;
	private int people;
	private long lastModified;

	public EstablishmentProvider() {
	}

	public EstablishmentProvider(String id) {
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
		return this.people;
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
	public List<Feature> getFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeProduct(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFeature(Feature feature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFeature(Feature feature) {
		// TODO Auto-generated method stub
		return false;
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

}
