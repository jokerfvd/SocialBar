package com.socialbar.android.radar;

/**
 * Interface <code>RadarEvents</code>.
 * Define eventos que ocorrem no mapa e que sao externalizados
 */
public interface RadarEvents {
	/**
	 * evento de mudança de localização
	 * 
	 * @param latitude
	 * @param longitude
	 */
	public void onRadarLocationChange(double latitude, double longitude);

	/**
	 * evento para click no marcador
	 * 
	 * @param id
	 */
	public void onRadarInfoWindowClick(String id);
}
