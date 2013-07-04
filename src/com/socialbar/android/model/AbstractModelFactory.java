package com.socialbar.android.model;

import com.socialbar.android.model.provider.FactoryProvider;

public class AbstractModelFactory {
	public static Model getInstance(){
		return FactoryProvider.getInstance();
	}
}
