package com.socialbar.android.activities.advance.resources;

import android.app.Activity;
/**
 * Abstract Class <code>GenericActivity</code>.
 */
public abstract class GenericActivity {
	private Activity activity;
	public GenericActivity(Activity activity){
		this.activity = activity;
	}
	/**
	 * Metodo para o efeito da saida do aplicativo
	 */
	public void finish(){
		this.activity.finish();		
	}
	/**
	 * Metodo para efeito quando a activity inicializa
	 */
	public abstract void resume();
	
	/**
	 * metodo privado ao pacote para pegar activity
	 * @return
	 */
	Activity getActivity() {
		return this.activity;
	}
}


