package com.socialbar.android.model;

/**
 * Interface <code>Feature</code>.
 */
public interface Feature {
	enum Type {
		// TODO: definir caracteristicas
	};

	String getName();

	Feature.Type getType();
	
	void setName(String name);

	void setType(Feature.Type type);

}
