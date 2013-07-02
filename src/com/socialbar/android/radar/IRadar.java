package com.socialbar.android.radar;

import java.util.List;

public interface IRadar {
	/**
	 * Adiciona pontos para o mapa
	 * @param pointer
	 */
	void addMarker(Pointer pointer);
	/**
	 * Adiciona uma lista de pontos para o mapa
	 * @param pointers
	 */
	void addMakers(List<Pointer> pointers);
	/**
	 * Remove um ponto especifico
	 * @param pointer
	 * @return
	 */
	boolean removeMarker(Pointer pointer);
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
}
