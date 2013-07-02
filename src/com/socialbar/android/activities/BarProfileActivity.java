package com.socialbar.android.activities;

import com.socialbar.android.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class BarProfileActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_profile);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		Button expandable = (Button) findViewById(R.id.expandable);
		expandable.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.checkin:
			// Intent intent = new Intent(this, Radar.class);
			// startActivity(intent);
			break;
		case R.id.expandable:
			this.changeDescriptionHeight(v);
			break;
		default:
			break;
		}
	}
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
		this.finish();
		this.overridePendingTransition(R.anim.out_enter, R.anim.out_leave);
	}

	/**
	 * Variavel para armazenar o valor de altura padrao setado no XML
	 */
	private int expanded;

	/**
	 * Metodo para mudar o tamanho do elemento de descri��o
	 * 
	 * @param v
	 */
	private void changeDescriptionHeight(View v) {
		Button bt = (Button) v;
		TextView textview = (TextView) findViewById(R.id.description_text);

		int size = textview.getHeight();

		if (expanded == 0)// seta o valor do XML
			expanded = size;

		Toast.makeText(v.getContext(), String.valueOf(size), Toast.LENGTH_SHORT)
				.show();

		LayoutParams params;

		if (expanded == size) {

			// Seta novo texto para o botao
			bt.setText(v.getContext().getResources()
					.getString(R.string.button_expandable_less));

			// Seta parametros para a aumentar o tamanho
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 1f);

		} else {
			bt.setText(v.getContext().getResources()
					.getString(R.string.button_expandable_more));
			params = new LayoutParams(LayoutParams.MATCH_PARENT, expanded, 1f);
		}

		textview.setLayoutParams(params);// aplica novo tamanho
	}

}
