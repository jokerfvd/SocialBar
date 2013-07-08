package com.socialbar.android.activities;

import java.sql.Date;

import java.text.SimpleDateFormat;


import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.model.dummy.FactoryDummy;


import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class BarProfileActivity extends Activity implements OnClickListener, ModelEvent {
	/**
	 * Funcoes genericas utilizadas por um conjunto de activities
	 */
	private GenericActivity genericActivity;
	private BroadcastReceiver broadCastReceiver;
	private Establishment establishment;
	/** Called when the activity is first created.
	 *  */
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
		Button expandable = (Button) findViewById(R.id.btn_expandable);
		expandable.setOnClickListener(this);
		
		ImageButton favorite = (ImageButton) findViewById(R.id.btn_favorite);
		favorite.setOnClickListener(this);
		
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		this.configuration();
	};
	/**
	 * metodo basico de configuracao onCreate
	 */
	private void configuration() {
		String id = getIntent().getExtras().getString("ID");
		if (id != null) {
			this.setEstablishment(id);		
		}
	}
	/**
	 * metodo para setar o estabelecimento da atividade
	 * @param id
	 */
	private void setEstablishment(String id){
		//Model model = AbstractModelFactory.getInstance();
		//broadCastReceiver = model.getEstablishmentPrototype(this, id);
		Model model = AbstractModelFactory.getInstance("dummy");
		Establishment es = model.getEstablishment(id);
		this.onModelReceive(Establishment.class, es);
	}
	/**
	 * metod para setar a estrela do favorito
	 * @param favorite
	 */
	private void setFavoriteImage(ImageButton favorite){
		if(this.establishment.isFavorite())
			favorite.setImageResource(R.drawable.icon_rating_important);
		else
			favorite.setImageResource(R.drawable.icon_rating_not_important);
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_checkin:
			// Intent intent = new Intent(this, Radar.class);
			// startActivity(intent);
			break;
		case R.id.btn_expandable:
			this.changeDescriptionHeight(v);
			break;
		case R.id.btn_favorite:
			this.changeFavorite(v);
			break;
		default:
			break;
		}
	}
	/**
	 * metodo para mudar estado do modelo estabelecimento
	 * @param v
	 */
	private void changeFavorite(View v) {
		this.establishment.setFavorite(!this.establishment.isFavorite());
		this.setFavoriteImage((ImageButton)v);
		Model model = AbstractModelFactory.getInstance("dummy");
		((FactoryDummy)model).save();
		Toast.makeText(this, getString(R.string.dialog_favorite_state_saved)+this.establishment.getName(), Toast.LENGTH_SHORT).show();
	}
	/**
	 * criação de icones da actionbar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.menu_bar_profile, menu);
		return true;
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
		case R.id.menu_refresh:
			Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_edit:
			Intent intent = new Intent(this, EditBarActivity.class);
			intent.putExtra("ID",this.establishment.getID());
			startActivity(intent);
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
	@Override
	public void onModelReceive(Class c, Object data) {

			this.establishment = (Establishment)data;
			SimpleDateFormat dfmt = new SimpleDateFormat("EEEE, d MMMM yyyy");  
	        Date date= new Date(this.establishment.getLastModified()); 
			
			((TextView) findViewById(R.id.text_name)).setText(this.establishment.getName());
			((TextView) findViewById(R.id.text_people)).setText(getString(R.string.bar_population_symbol)+String
					.valueOf(this.establishment.getPeople()));
			((TextView) findViewById(R.id.text_last_modified)).setText(getString(R.string.bar_last_modified)+" "+dfmt.format(date));
			((TextView) findViewById(R.id.text_address)).setText(this.establishment.getAddress());
			
			//botao
			ImageButton favorite = (ImageButton) findViewById(R.id.btn_favorite);
			favorite.setOnClickListener(this);
			this.setFavoriteImage(favorite);
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		if (broadCastReceiver != null) {
			try {
				this.unregisterReceiver(broadCastReceiver);
			} catch (IllegalArgumentException e) {
				Log.i("onPause", e.getLocalizedMessage(), e);
			}
		}
	}

}
