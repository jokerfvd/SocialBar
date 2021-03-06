package com.socialbar.android.radar;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

import com.google.android.gms.internal.p;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Class <code>GoogleRadar</code> implementacao do Radar.
 */
public class GoogleRadar implements Radar, OnMyLocationChangeListener,
		OnInfoWindowClickListener {

	private GoogleMap map;
	private List<Marker> markers;
	private List<Pointer> pointers;
	private RadarEvents listener;
	private String clickedMarkerID;
	private boolean savedActiveInfoView;

	/**
	 * Construtor recebendo o mapa
	 * 
	 * @param map
	 */
	public GoogleRadar(GoogleMap map) {
		this.map = map;
		this.initilize();
	}

	/**
	 * Inicializa propriedades do mapa
	 */
	private void initilize() {
		this.initializePointersAndMarkers();
		this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		this.map.setMyLocationEnabled(true);
		this.map.setOnMyLocationChangeListener(this);
		this.map.setOnInfoWindowClickListener(this);		
	}
	/**
	 * inicializa as colecoes pontos e marcas
	 */
	private void initializePointersAndMarkers(){
		this.markers = new ArrayList<Marker>();
		this.pointers = new ArrayList<Pointer>();
	}

	/**
	 * Metodo para a chamada o evento setOnMyLocationChangeListener
	 */
	@Override
	public void onMyLocationChange(Location location) {
		// definicao de zoom e localizacao
		CameraUpdate myLoc = CameraUpdateFactory
				.newCameraPosition(new CameraPosition.Builder()
						.target(new LatLng(location.getLatitude(), location
								.getLongitude())).zoom(16).build());
		// commita as alteracoes na camera
		this.map.moveCamera(myLoc);

		// remove a inscricao no evento de buscar locacao
		this.map.setOnMyLocationChangeListener(null);

		// avisa o observador sobre a localizacao do dispositivo
		if (this.listener != null)
			this.listener.onRadarLocationChange(location.getLatitude(),
					location.getLongitude());
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		this.clickedMarkerID = marker.getSnippet();
		//avisa o observador sobre o click no infowindow
		if (this.listener != null)
			this.listener.onRadarInfoWindowClick(marker.getSnippet());

	}

	/**
	 * Metodos de interface Radar
	 */

	@Override
	public void addMarker(Pointer pointer) {
		MarkerOptions mark = new MarkerOptions();
		mark.position(new LatLng(pointer.getLatitude(), pointer.getLongitude()));
		mark.title(pointer.getTitle());
		mark.snippet(pointer.getSnippet());
		mark.icon(BitmapDescriptorFactory.fromResource(pointer.getIconId()));
		Marker marker = map.addMarker(mark);

		if (pointer.getSnippet().equals(this.clickedMarkerID))
			marker.showInfoWindow();
		
		pointers.add(pointer);
		markers.add(marker);
	}

	@Override
	public void addMakers(List<Pointer> pointers) {
		for (Pointer pointer : pointers)
			this.addMarker(pointer);
	}

	@Override
	public boolean removeMarker(Pointer pointer) {
		for (Marker marker : markers) {
			if (pointer.equals(marker.getSnippet())) {
				marker.remove();
				return markers.remove(marker);
			}
		}
		this.pointers.remove(pointer);
		return false;
	}

	@Override
	public void clearMap() {
		map.clear();
	}

	@Override
	public void setRadarEventListener(RadarEvents listener) {
		this.listener = listener;
	}

	@Override
	public void setInfoWindow(Object info) {
		this.map.setInfoWindowAdapter((InfoWindowAdapter) info);
	}

	@Override
	public void updateRadarState() {
		if (this.savedActiveInfoView) {
			this.clearMap();
			List<Pointer> list = this.pointers;
			this.initializePointersAndMarkers();
			this.addMakers(list);
			this.savedActiveInfoView = false;
		}
	}

	@Override
	public void saveRadarState() {
		this.savedActiveInfoView = true;

	}

	@Override
	public void removeRadarState() {
		this.savedActiveInfoView = false;
		this.clickedMarkerID = null;
		
	}

}
