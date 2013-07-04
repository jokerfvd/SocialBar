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
import android.view.View;
import android.view.View.OnClickListener;
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

public class RadarActivity extends Activity implements OnClickListener,RadarEvents {

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
		 * Botoes
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
		// inicia a exebição do mapa
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (resultCode == ConnectionResult.SUCCESS) {
			if (radar == null) {
				radar = new GoogleRadar(myMapFragment.getMap());
				if (radar != null) {
					radar.setRadarEventListener(this);
					radar.setInfoWindow(this.createInfoWindowAdapter());
				}
			}
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}
	
	private InfoWindowAdapter createInfoWindowAdapter(){
		return new InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker marker) {              
                return null;
            }           

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.snippet_radar_info_window, null);

                // Getting reference to the TextView to set title
                TextView note = (TextView) v.findViewById(R.id.tv_lat);

                note.setText(marker.getTitle() );

                // Returning the view containing InfoWindow contents
                return v;
            }
        };
	}
	
	//eventos do radar
	@Override
	public void onRadarLocationChange(double latitude, double longitude) {
		Model model = AbstractModelFactory.getInstance();
		List<Establishment> es = model.getEstablishment( longitude,latitude);
		Log.i("RADAR",String.valueOf(es.size()));
		radar.addMakers(GooglePointer.getPointer(es, R.drawable.bar_mark));		
	}
	@Override
	public void onRadarInfoWindowClick(String id) {
		Intent intent = new Intent(this, BarProfileActivity.class);
		intent.putExtra("ID",id);
		startActivity(intent);
	}
}
