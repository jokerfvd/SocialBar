package com.socialbar.android.model;

import java.util.Locale;

import com.socialbar.android.model.dummy.FactoryDummy;
import com.socialbar.android.model.provider.FactoryProvider;

public class AbstractModelFactory {
	public static Model getInstance(String... factory){
		if(factory.length > 0 &&  factory[0].toUpperCase().equals("DUMMY"))
			return FactoryDummy.getInstance();
		return FactoryProvider.getInstance();
	}
	
}
