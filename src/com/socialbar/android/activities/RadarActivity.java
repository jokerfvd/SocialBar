package com.socialbar.android.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
	private double latitude;
	private double longitude;

	// armazenamento temporario
	private List<Establishment> establishments;

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
		this.genericActivity.strictMode();

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
	 * criação de icones da actionbar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_bar_update, menu);
		return true;
	}

	/**
	 * evento voltar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			this.radar.removeRadarState();
			this.reloadRadar();
			return true;
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
		// Utility class for verifying that the Google Play services APK is
		// available and up-to-date on this device
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		// Contains all possible error codes for when a client fails to connect
		// to Google Play services
		if (resultCode == ConnectionResult.SUCCESS) {
			if (this.radar == null) {
				this.radar = new GoogleRadar(myMapFragment.getMap());
				if (this.radar != null) {
					this.radar.setRadarEventListener(this);
					this.radar.setInfoWindow(this.createInfoWindowAdapter());
				}
			} else {
				// this.radar.updateRadarState();
				this.reloadRadar();
			}
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}

	/**
	 * metodo para a recarga do radar, seja pelo botao reload ou pela troca de
	 * activity
	 */
	private void reloadRadar() {
		this.radar.clearMap();
		this.onRadarLocationChange(this.latitude, this.longitude);
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

				Establishment es = findEstablishmentById(marker.getSnippet());

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

	/**
	 * metodo utilizado para encontrar um estabelecimento valido por id na lista
	 * de todos os estabelecimentos carregados do site utlizado pela infowindow
	 */
	private Establishment findEstablishmentById(String id) {
		if (this.establishments != null)
			for (Establishment e : this.establishments)
				if (id.equals(e.getID()))
					return e;
		return null;
	}

	// eventos do radar
	/**
	 * quando a latitude e longitude do mapa for capturada a activity deve
	 * requisitar os pontos ao modelo passando as coordenadas
	 * 
	 */
	@Override
	public void onRadarLocationChange(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.establishments = this.getModelInstance().getEstablishment(
				latitude, longitude);
		radar.addMakers(GooglePointer.getPointer(this.establishments,
				R.drawable.bar_mark));
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

	/**
	 * configuração de obtencao do modelo localizada
	 * 
	 * @return
	 */
	private Model getModelInstance() {
		return AbstractModelFactory.getInstance("real");
	}
}
