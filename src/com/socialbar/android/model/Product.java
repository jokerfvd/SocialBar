package com.socialbar.android.model;

/**
 * Interface <code>Product</code>.
 */
public interface Product {
	enum Type {
		// TODO: definir tipos de produtos
	};

	String getName();

	int getPrice();

	Product.Type getType();

	void setName(String name);

	void getPrice(int price);

	void setType(Product.Type type);	
	
}
