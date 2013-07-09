package com.socialbar.android.model.dummy;

import java.io.Serializable;

import com.socialbar.android.model.Product;


/**
 * Class <code>Produto postico - serializavel</code>.
 */
public class DummyProduct implements Product,Serializable{	

	private static final long serialVersionUID = 1L;
	private String name;
	private double price;
	private Type type;
	
	public DummyProduct(String name, double price) {
		this.name = name;
		this.price = price;
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public void setPrice(double price) {
		this.price = price;		
	}

	@Override
	public void setType(Type type) {
		this.type = type;		
	}

}
