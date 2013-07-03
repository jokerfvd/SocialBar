package com.socialbar.android.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.radar.Radar;
import com.socialbar.android.radar.GoogleRadar;

public class RadarActivity extends Activity implements OnClickListener{
	
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
		//setContentView(R.layout.activity_radar);

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
	/**
	 * metodo de configuração
	 */
	private void configuration(){
		
		
		
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
		
	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS) {
			// Toast.makeText(getApplicationContext(),"isGooglePlayServicesAvailable SUCCESS",
			// Toast.LENGTH_SHORT).show();

			if (radar == null) {
				GoogleMap map = myMapFragment.getMap();
				if (map != null) {
					radar = new GoogleRadar(map,this);
					
				}
			}

		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
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
}
