package com.socialbar.android.radar;

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
