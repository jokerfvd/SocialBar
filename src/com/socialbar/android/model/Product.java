package com.socialbar.android.model;

/**
 * Interface <code>Product</code>.
 */
public interface Product {
	enum Type {
		// TODO: definir tipos de produtos
	};

	String getName();

	double getPrice();

	Product.Type getType();

	void setName(String name);

	void setPrice(double price);

	void setType(Product.Type type);	
	
}
