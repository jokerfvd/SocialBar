package com.socialbar.android.model.dummy;

import java.io.Serializable;

import com.socialbar.android.model.Feature;
import com.socialbar.android.model.Product.Type;

/**
 * Class <code>Caracteristica postico - serializavel</code>.
 */
public class DummyFeature implements Feature,Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private Type type;
	
	public DummyFeature(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
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
	public void setType(Type type) {
		this.type = type;
	}

}
