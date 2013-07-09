package com.socialbar.android.radar;

/**
 * Interface <code>Pointer</code>.
 * Define os dados dos pontos para serem exibidos no radar
 */
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
	 * icone a ser exibido no radar
	 * 
	 * @return
	 */
	int getIconId();

}
