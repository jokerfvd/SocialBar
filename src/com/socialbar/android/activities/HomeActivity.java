package com.socialbar.android.activities;

import com.socialbar.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class HomeActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		/**
		 * botoes
		 */
		this.findViewById(R.id.rest).setOnClickListener(this);
		this.findViewById(R.id.newbar).setOnClickListener(this);
		this.findViewById(R.id.radar).setOnClickListener(this);
		this.findViewById(R.id.favorites).setOnClickListener(this);
		this.findViewById(R.id.search).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		
		/**
		 * acao dos botoes
		 */
		switch (v.getId()) {
		case R.id.rest:
			intent = new Intent(this, DummyMainActivity.class);
			startActivity(intent);
			break;
		case R.id.newbar:
			intent = new Intent(this, NewBarActivity.class);
			startActivity(intent);
			break;
		case R.id.radar:
			intent = new Intent(this, RadarActivity.class);
			startActivity(intent);
			break;
		case R.id.search:
			intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.favorites:
			intent = new Intent(this, FavoritesActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
}
