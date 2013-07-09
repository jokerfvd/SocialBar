package com.socialbar.android.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.radar.GooglePointer;
import com.socialbar.android.radar.GoogleRadar;
import com.socialbar.android.radar.Radar;
import com.socialbar.android.radar.RadarEvents;
/**
 * Activity <code>Exibe o mapa</code>.
 */
public class RadarActivity extends Activity implements OnClickListener,
		RadarEvents {

	private GenericActivity genericActivity;
	private MapFragment myMapFragment;
	private static final String TAG_MYMAPFRAGMENT = "TAG_MyMapFragment";
	private final int RQS_GooglePlayServices = 1;
	private Radar radar;

	/** Called when the activity is first created. */
	/** configuração basica */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_radar);

		/**
		 * barra manipulação
		 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		/**
		 * efeitos da activity
		 */

		this.genericActivity = new GenericActivitySlider(this);
		this.genericActivity.resume();

		/**
		 * configuracao do oncreate
		 */
		this.configuration();

	}

	@Override
	protected void onResume() {
		super.onResume();
		this.exec();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/**
	 * evento voltar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.onBackPressed();// encerra a activity
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		this.genericActivity.finish();
	}

	/**
	 * metodo de configuração, executado no onCreate
	 */
	private void configuration() {
		// toma o fragmento
		FragmentManager myFragmentManager = getFragmentManager();
		myMapFragment = (MapFragment) myFragmentManager
				.findFragmentByTag(TAG_MYMAPFRAGMENT);
		if (myMapFragment == null) {
			myMapFragment = MapFragment.newInstance();
			FragmentTransaction fragmentTransaction = myFragmentManager
					.beginTransaction();
			fragmentTransaction.add(android.R.id.content, myMapFragment,
					TAG_MYMAPFRAGMENT);
			fragmentTransaction.commit();
		}
	}

	/**
	 * metodo para execucao, executado no onResume
	 */
	private void exec() {
		//Utility class for verifying that the Google Play services APK is available and up-to-date on this device
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
		//Contains all possible error codes for when a client fails to connect to Google Play services
		if (resultCode == ConnectionResult.SUCCESS) {
			if (radar == null) {
				radar = new GoogleRadar(myMapFragment.getMap());
				if (radar != null) {
					radar.setRadarEventListener(this);
					radar.setInfoWindow(this.createInfoWindowAdapter());
				}
			} else {
				this.radar.updateRadarState();
			}
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}

	/**
	 * exibe a janela acima da marca no mapa
	 * 
	 * @return
	 */
	private InfoWindowAdapter createInfoWindowAdapter() {
		return new InfoWindowAdapter() {
			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {

				View v = getLayoutInflater().inflate(
						R.layout.snippet_radar_info_window, null);

				Model model = AbstractModelFactory.getInstance("dummy");
				Establishment es = model.getEstablishment(marker.getSnippet());

				((TextView) v.findViewById(R.id.text_name)).setText(es
						.getName());
				((TextView) v.findViewById(R.id.text_people))
						.setText(getString(R.string.bar_population_symbol)
								+ es.getPeople());
				((TextView) v.findViewById(R.id.text_phone)).setText(es
						.getPhoneNumber());

				if (es.isFavorite())
					((ImageView) v.findViewById(R.id.img_favorite))
							.setVisibility(View.VISIBLE);

				return v;
			}
		};
	}

	// eventos do radar
	/**
	 * quando a latitude e longitude do mapa for capturada
	 * a activity deve requisitar os pontos ao modelo passando as coordenadas
	 * 
	 */
	@Override
	public void onRadarLocationChange(double latitude, double longitude) {
		Model model = AbstractModelFactory.getInstance("dummy");
		List<Establishment> es = model.getEstablishment(latitude, longitude);
		Log.i("RADAR", String.valueOf(es.size()));
		radar.addMakers(GooglePointer.getPointer(es, R.drawable.bar_mark));
	}
	/**
	 * evento para a definição do clique sobre a infowindow no mapa
	 */	
	@Override
	public void onRadarInfoWindowClick(String id) {
		this.radar.saveRadarState();
		Intent intent = new Intent(this, BarProfileActivity.class);
		intent.putExtra("ID", id);
		startActivity(intent);
	}

	// extra
	/**
	 * apenas um teste para capturar o evento de long click no mapa
	 * o objetivo seria adicionar criação de pontos direto no mapa
	 * e a movimentacao de pontos
	 * @param ev
	 */
	public void recieveLongClick(MotionEvent ev) {
		System.out.println("radar long click");
		// You can now pull lat/lng from geoPoint
	}
}
