package com.socialbar.android.model.dummy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * Servico para simular um content provider
 * 
 * 
 */
public class ServiceDummy {
	private StorageDummy storage;
	

	public ServiceDummy(Context context) {
		super();
		
	}

	/**
	 * verifica se o arquivo existe
	 */
	
}
