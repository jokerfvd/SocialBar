package com.socialbar.android.activities;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class BarProfileActivity extends Activity implements OnClickListener {
	/**
	 * Funcoes genericas utilizadas por um conjunto de activities
	 */
	private GenericActivity genericActivity;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_profile);

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
		Button expandable = (Button) findViewById(R.id.expandable);
		expandable.setOnClickListener(this);

		this.configuration();
	}

	private void configuration() {
		String id = getIntent().getExtras().getString("ID");
		if (id != null) {
			Model model = AbstractModelFactory.getInstance();
			Establishment e = model.getEstablishment(id);
			
			
			SimpleDateFormat dfmt = new SimpleDateFormat("EEEE, d MMMM yyyy");  
	        Date date= new Date(e.getLastModified()); 
			
			((TextView) findViewById(R.id.name)).setText(e.getName());
			((TextView) findViewById(R.id.people)).setText(String
					.valueOf(e.getPeople()));
			((TextView) findViewById(R.id.last_modified)).setText("atualizado em "+dfmt.format(date));
			((TextView) findViewById(R.id.address)).setText(e.getAddress());
		}
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
	 * Variavel para armazenar o valor de altura padrao setado no XML
	 */
	private int expanded;

	/**
	 * Metodo para mudar o tamanho do elemento de descrição
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
