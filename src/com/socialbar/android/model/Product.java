package com.socialbar.android.model;

import java.math.BigDecimal;

/**
 * Interface <code>Product</code>.
 */
public interface Product {
	enum Type {
		// TODO: definir tipos de produtos
	};

	String getName();

	BigDecimal getPrice();

	Product.Type getType();

	void setName(String name);

	void getPrice(BigDecimal price);

	void setType(Product.Type type);	
	
}
