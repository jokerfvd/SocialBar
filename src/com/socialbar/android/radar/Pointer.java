package com.socialbar.android.radar;

public interface Pointer {
	/**
	 * latitude
	 * 
	 * @return
	 */
	double getLatitude();

	/**
	 * longitude
	 * 
	 * @return
	 */
	double getLongitude();

	/**
	 * metodo para id
	 * 
	 * @return
	 */
	String getSnippet();

	/**
	 * metodo para o nome
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * icone a ser exibido no mapa
	 * 
	 * @return
	 */
	int getIconId();

}
