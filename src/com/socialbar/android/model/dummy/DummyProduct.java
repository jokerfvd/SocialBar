package com.socialbar.android.model.dummy;

import java.io.Serializable;

import com.socialbar.android.model.Product;



public class DummyProduct implements Product,Serializable{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPrice(int price) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(Type type) {
		// TODO Auto-generated method stub
		
	}

}
