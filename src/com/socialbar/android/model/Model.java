package com.socialbar.android.model;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;

/**
 * Interface <code>Model</code>. Interface para todos os objetos - basicas
 */
public interface Model {

	/**
	 * Adicionar um novo estabelecimento
	 */
	public void addEstablishment(Establishment establishment);

	/**
	 * Atualiza
	 */
	public void updateEstablishment(Establishment establishment);

	/**
	 * Pegar estabelecimento novo
	 */
	Establishment getEstablishment();

	/**
	 * pegar estabelecimento com id
	 * 
	 * @param id
	 *            id do estabelecimento
	 * @return
	 */
	Establishment getEstablishment(String id);

	/**
	 * pegar estabelecimentos por latitude e longitude
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	<T extends Establishment> List<T> getEstablishment(double latitude,
			double longitude);

	/**
	 * pegar estabelecimentos por parametros variaveis tipica busca de
	 * estabelecimentos
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	<T extends Establishment> List<T> getEstablishment(String... args);

	/**
	 * pegar usuario pode ser utilizada para fazer login no servidor
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	User getUser(String name, String password);

	/**
	 * pegar um usuario novo
	 * 
	 * @return
	 */
	User getUser();

	/**
	 * pegar lista de favoritos
	 * 
	 * @return
	 */
	<T extends Establishment> List<T> getFavorites();

	public void setEstablishmentFavorite(Establishment establishment);

	/**
	 * pegar BroadCast ja pronto
	 * 
	 * @param me
	 * @param id
	 * @return
	 */
	BroadcastReceiver getEstablishmentPrototype(ModelEvent me, String id);

	/**
	 * Setar o contexto da aplicacao
	 * 
	 * @param context
	 */
	void setContext(Context context);

}
