package com.socialbar.android.model;

/**
 * Interface <code>Feature</code>.
 * 
 */
public interface Feature {
	/**
	 * Tipo de caraceteristica
	 * 
	 * @author Felipe
	 * 
	 */
	enum Type {
		// TODO: definir caracteristicas
	};

	/**
	 * pegar nome da caracteristica
	 * 
	 * @return
	 */
	String getName();

	/**
	 * pegar tipo da caracteristica
	 * 
	 * @return
	 */
	Feature.Type getType();

	/**
	 * setar nome da caracteristica
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * setar o tipo da caracteristica
	 * 
	 * @param type
	 */
	void setType(Feature.Type type);

}
