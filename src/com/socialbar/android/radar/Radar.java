package com.socialbar.android.radar;

import java.util.List;

/**
 * Interface <code>Radar</code>.
 */
public interface Radar {
	/**
	 * Adiciona pontos para o mapa
	 * 
	 * @param pointer
	 */
	void addMarker(Pointer pointer);

	/**
	 * Adiciona uma lista de pontos para o mapa
	 * 
	 * @param pointers
	 */
	void addMakers(List<Pointer> pointers);

	/**
	 * Remove um ponto especifico
	 * 
	 * @param pointer
	 * @return
	 */
	boolean removeMarker(Pointer pointer);

	/**
	 * Remove todos os pontos do mapa
	 * 
	 * @return
	 */
	void clearMap();

	/**
	 * Adicionar janela de informacao ao mapa
	 * 
	 * @param info
	 */
	void setInfoWindow(Object info);

	/**
	 * evento de click a janela de informação
	 * 
	 * @param listener
	 */
	void setRadarEventListener(RadarEvents listener);
}
