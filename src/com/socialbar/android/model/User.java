package com.socialbar.android.model;
/**
 * Interface <code>User</code>.
 */
public interface User {
	String getName();
	String getToken();//token de sessao
	boolean IsAuthenticated();
	
	/**
	 * coordenada atual do usuário, calculada pelo GPS	
	 */
	double getLatitude();
	double getLongitude();
	
	void setName(String nome);
	void setPassword(String senha);
}
