package com.socialbar.android.radar;

import java.util.List;

import com.google.android.gms.maps.GoogleMap;
import com.socialbar.android.model.Establishment;
/**
 * Interface <code>Radar</code>.
 */
public interface Radar {
	/**
	 * Adiciona pontos para o mapa
	 * @param pointer
	 */
	void addMarker(Establishment pointer);
	/**
	 * Adiciona uma lista de pontos para o mapa
	 * @param pointers
	 */
	void addMakers(List<Establishment> pointers);
	/**
	 * Remove um ponto especifico
	 * @param pointer
	 * @return
	 */
	boolean removeMarker(Establishment pointer);
	/**
	 * Remove todos os pontos do mapa
	 * @return
	 */
	boolean clearMap();
	/**
	 * Retorna a localizacao do usuario
	 * @return
	 */
	float[] getUserLocation();
	
	GoogleMap getMap();
}
