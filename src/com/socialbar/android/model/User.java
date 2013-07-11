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
	
	String getLogin();

	/**
	 * pegar o token de autenticacao do usuario
	 * 
	 * @return
	 */
	String getToken();// token de sessao
	
	/**
	 * setar token de autenticacao do usuario
	 * 
	 * @param token
	 */
	void setToken(String token);

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
	 * @param name
	 */
	void setName(String name);
	
	void setLogin(String login);

	/**
	 * setar senha para o usuario
	 * 
	 * @param senha
	 */
	void setPassword(String senha);
	
	String getId();

}
