package com.socialbar.android.activities;

import com.socialbar.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button newbar = (Button) findViewById(R.id.newbar);
		Button radar = (Button) findViewById(R.id.radar);
		newbar.setOnClickListener(this);
		radar.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.newbar:
			intent = new Intent(this, BarProfileActivity.class);
			startActivity(intent);
			break;
		case R.id.radar:
			intent = new Intent(this, RadarActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
}
