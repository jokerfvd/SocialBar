package com.socialbar.android.radar;

import java.util.List;

import android.location.Location;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Radar implements IRadar, OnMyLocationChangeListener {
	private GoogleMap map;
	private double user_long;
	private double user_lat;
	private int image_resource;
	
	/**
	 * Construtor recebendo o mapa
	 * @param map
	 */
	public Radar(GoogleMap map) {
		this.map = map;
		this.initilize();
	}
	
	/**
	 * Construtor recebendo o mapa e o ponteiro para a imagem do icone para ser mostrado no mapa
	 * @param map
	 */
	public Radar(GoogleMap map, int resource) {
		this.map = map;
		this.image_resource = resource;
		this.initilize();
	}

	/**
	 * Inicializa propriedades do mapa
	 */
	private void initilize() {
		this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		this.map.setMyLocationEnabled(true);
		this.map.setOnMyLocationChangeListener(this);
	}

	/**
	 * metodo para gerar pontos
	 * 
	 * @param firstloc
	 */
	private void generateDummyLocations() {
		// realiza incremento nos pontos e chama a adicao no mapa
		for (int i = 1; i < 15; i++) {
			this.addmark(this.user_lat + 0.001 * i, this.user_long + 0.001 * i,
					"Label " + String.valueOf(i), i);
		}
	}

	/**
	 * Metodo para adicionar ponto no mapa
	 * 
	 * @param lat
	 * @param log
	 * @param text
	 * @param qt
	 */
	private void addmark(double lat, double log, String text, int qt) {
		// Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		// Bitmap bmp = Bitmap.createBitmap(58, 80, conf);
		// Canvas canvas1 = new Canvas(bmp);

		// paint defines the text color,
		// stroke width, size
		// Paint color = new Paint();
		// color.setTextSize(18);
		// color.setFakeBoldText(true);
		// color.setColor(Color.BLACK);
		//
		// //modify canvas
		// canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
		// R.drawable.bar_mark), 0,0, color);
		// canvas1.drawText(String.valueOf(qt), 22, 57, color);

		MarkerOptions mark = new MarkerOptions();
		mark.position(new LatLng(lat, log));
		mark.title(text);
		mark.snippet("pessoas: " + String.valueOf(qt));
		mark.icon(BitmapDescriptorFactory.fromResource(image_resource));
		map.addMarker(mark);
	}
	
	/**
	 * Metodo interno para definir a localizacao do usuario
	 * @param location
	 */
	private void setUserLocation(Location location) {
		this.user_lat = location.getLatitude();
		this.user_long = location.getLongitude();
	}
	
	/**
	 * Metodo para a chamada o evento setOnMyLocationChangeListener
	 */
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

		this.setUserLocation(location);

		// gera pontos para popular o mapa
		this.generateDummyLocations();

	}

	/**
	 * Metodos de interface
	 */

	@Override
	public void addMarker(Pointer pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMakers(List<Pointer> pointers) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeMarker(Pointer pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float[] getUserLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean clearMap() {
		// TODO Auto-generated method stub
		return false;
	}

}
