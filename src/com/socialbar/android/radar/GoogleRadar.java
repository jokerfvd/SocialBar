package com.socialbar.android.radar;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

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
import com.socialbar.android.R;
import com.socialbar.android.activities.BarProfileActivity;
import com.socialbar.android.model.Establishment;
/**
 * Class <code>GoogleRadar</code>.
 */
public class GoogleRadar implements Radar, OnMyLocationChangeListener {
	private GoogleMap map;
	private double userLongitude;
	private double userLatitude;
	private int iconResource;
	private int layoutResource;
	private Activity activity;
	
	/**
	 * Construtor recebendo o mapa
	 * @param map
	 */
	public GoogleRadar(GoogleMap map) {
		this.map = map;
		this.initilize();
	}
	
	/**
	 * Construtor recebendo o mapa e o ponteiro para a imagem do icone para ser mostrado no mapa
	 * @param map
	 */
	public GoogleRadar(GoogleMap map, int icon, int layout) {
		this.map = map;
		this.iconResource = icon;
		this.layoutResource = layout;
		this.initilize();
	}
	public GoogleRadar(GoogleMap map, Activity activity) {
		this.map = map;
		this.activity = activity;
		this.iconResource = R.drawable.bar_mark;
		this.layoutResource = R.layout.radar_info;
		this.initilize();
		this.initialize2();
		
	}
	

	/**
	 * Inicializa propriedades do mapa
	 */
	private void initilize() {
		this.map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		this.map.setMyLocationEnabled(true);
		this.map.setOnMyLocationChangeListener(this);				
	}
	private void initialize2 () {
		this.map.setInfoWindowAdapter(new InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker marker) {              
                return null;
            }           

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = activity.getLayoutInflater().inflate(R.layout.radar_info, null);

                // Getting reference to the TextView to set title
                TextView note = (TextView) v.findViewById(R.id.tv_lat);

                note.setText(marker.getTitle() );

                // Returning the view containing InfoWindow contents
                return v;

            }

        });
		this.map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
               Intent intent = new Intent(activity.getBaseContext(), BarProfileActivity.class);
               activity.startActivity(intent);
            }
        });
	}

	/**
	 * metodo para gerar pontos
	 * 
	 * @param firstloc
	 */
	private void generateDummyLocations() {
		// realiza incremento nos pontos e chama a adicao no mapa
		for (int i = 1; i < 15; i++) {
			this.addmark(this.userLatitude + 0.001 * i, this.userLongitude + 0.001 * i,
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
		mark.icon(BitmapDescriptorFactory.fromResource(iconResource));
		map.addMarker(mark);
	}
	
	/**
	 * Metodo interno para definir a localizacao do usuario
	 * @param location
	 */
	private void setUserLocation(Location location) {
		this.userLatitude = location.getLatitude();
		this.userLongitude = location.getLongitude();
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
	public void addMarker(Establishment pointer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMakers(List<Establishment> pointers) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeMarker(Establishment pointer) {
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

	@Override
	public GoogleMap getMap() {
		// TODO Auto-generated method stub
		return map;
	}
	
	

}
