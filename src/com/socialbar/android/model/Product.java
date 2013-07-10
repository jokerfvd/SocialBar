package com.socialbar.android.model;

/**
 * Interface <code>Product</code>.
 */
public interface Product {
	/**
	 * Tipo de produto
	 * 
	 * @author Felipe
	 * 
	 */
	enum Type {
		// TODO: definir tipos de produtos
	};

	/**
	 * pegar nome
	 * 
	 * @return
	 */
	String getName();

	/**
	 * pegar o preco do produto
	 * 
	 * @return
	 */
	double getPrice();

	/**
	 * pegar o tipo do produto
	 * 
	 * @return
	 */
	Product.Type getType();

	/**
	 * setar o nome do produto
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * setar o preco do produto
	 * 
	 * @param price
	 */
	void setPrice(double price);

	/**
	 * setar o tipo do produto
	 * 
	 * @param type
	 */
	void setType(Product.Type type);

}
