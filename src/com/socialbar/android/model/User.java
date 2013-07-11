package com.socialbar.android.model;

/**
 * Interface <code>User</code>.
 */
public interface User {
	/**
	 * pegar o nome do usuario
	 * 
	 * @return
	 */
	String getName();

	/**
	 * pegar o token de autenticacao do usuario
	 * 
	 * @return
	 */
	String getToken();// token de sessao

	/**
	 * verificar se o usuario esta autenticado
	 * 
	 * @return
	 */
	boolean IsAuthenticated();

	/**
	 * latitude atual do usuário, calculada pelo GPS
	 */
	double getLatitude();

	/**
	 * latitude atual do usuário, calculada pelo GPS
	 */
	double getLongitude();

	/**
	 * setar nome para o usuario
	 * 
	 * @param nome
	 */
	void setName(String nome);

	/**
	 * setar senha para o usuario
	 * 
	 * @param senha
	 */
	void setPassword(String senha);
	
	String getId();

}
