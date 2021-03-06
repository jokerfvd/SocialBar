package com.socialbar.android.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.activities.advance.resources.GenericAdapter;
import com.socialbar.android.activities.advance.resources.GenericAdapterEvent;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;

/**
 * Activity <code>favoritos</code>.
 */
public class FavoritesActivity extends Activity implements OnClickListener, GenericAdapterEvent {
	private GenericActivity genericActivity;
	private GenericAdapter adapter;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

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
		 * configuracao inicial do oncreate
		 */
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		this.configuration();
		
	};
	/**
	 * metodo de configuracao
	 * realiza requisicao assincrona
	 */
	public void configuration() {
		List<Establishment> es = this.getModelInstance().getFavorites();
		this.onModelReceive(Establishment.class,es);	
	}
	/**
	 * metodo para receber dados assincronos
	 * @param c
	 * @param data
	 */
	public void onModelReceive(Class c, Object data) {
		final ListView listView = (ListView) findViewById(R.id.list_bar);			
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		List<Establishment> es = (List<Establishment>)data;
		this.adapter = new GenericAdapter(es, this);
		listView.setAdapter(this.adapter);
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
	//evento do adapter
	@Override
	public void onFavoriteChange(Establishment e) {
		e.setFavorite(!e.isFavorite());
		this.getModelInstance().setEstablishmentFavorite(e);
		this.configuration();
	}
	/**
	 * configuração de obtencao do modelo localizada
	 * @return
	 */
	private Model getModelInstance(){
		return AbstractModelFactory.getInstance("real");
	}
	

}
