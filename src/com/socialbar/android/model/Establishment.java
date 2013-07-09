package com.socialbar.android.model;

import java.util.List;

/**
 * Interface <code>Establishment</code>.
 */
public interface Establishment {

	enum Type {
		COMIDA, BEBIDA, SORVETE, RODIZIO
	};

	// leitura
	/** Getter for property "latitude" */
	double getLatitude();

	/** Getter for property "longitude" */
	double getLongitude();

	// informa��es
	String getID();

	String getName();

	String getDescription();

	void setLastModified(long miliseconds);

	long getLastModified();

	/** Getter for property <b>number of people</b> on establishment */
	int getPeople();

	String getAddress();

	String getPhoneNumber();

	/** Getter for all establishment's <b>features</b> */
	<T extends Feature>List<T> getFeatures();

	/** Getter for all establishment's announced <b>products</b> */
	<T extends Product>List<T> getProducts();

	// customiza��es do assinante
	/** Getter for custom establishment's <b>logo or icon image</b> */
	byte[] getIconImage();

	/** Getter for custom establishment's <b>welcome image</b> */
	byte[] getWelcomeImage();

	// escrita
	void addLikeIt(Object user);

	void removeLikeIt(Object user);

	void addComment(Object user);

	void removeComment(Object user);

	/*
	 * O checkin � feito manualmente pelo usu�rio, porem o checkout � feito
	 * automaticametequando o us�rio se afasta de estabelecimento.
	 */
	/** Setter for establishment's <b>number of people</b>. */
	boolean checkIn();

	void setName(String name);

	void setAddress(String address);

	void setPhoneNumber(String phoneNumber);

	void setLatitude(double latitude);

	void setLongitude(double longitude);

	boolean addProduct(Product product);

	boolean removeProduct(Product product);

	boolean addFeature(Feature feature);

	boolean removeFeature(Feature feature);
	
	boolean isFavorite();
	void setFavorite(boolean favorite);
	long getCreatedAt();
}
