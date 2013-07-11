package com.socialbar.android.activities;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.activities.advance.resources.GenericAdapter;
import com.socialbar.android.activities.advance.resources.GenericAdapterEvent;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
/**
 * Activity <code>tela de busca de estabelecimentos</code>.
 */
public class SearchActivity extends Activity implements OnClickListener,OnQueryTextListener,GenericAdapterEvent {
	private GenericActivity genericActivity;
	private GenericAdapter adapter;
	private SearchView searchView;
	private String keyword;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_bar_search, menu);		
		MenuItem searchViewMenuItem = menu.findItem(R.id.menu_search);		
		this.searchView = (SearchView) searchViewMenuItem.getActionView();
		this.searchView.setIconifiedByDefault(false);
		this.searchView.setOnQueryTextListener(this);
		this.searchView.requestFocus();
		return true;
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		this.keyword = query;
		this.configuration(this.keyword);
		this.searchView.clearFocus();
		return true;
	}
	/**
	 * configuracao apos entrada de busca
	 * @param str
	 */
	private void configuration(String str) {		
		List<Establishment> es = this.getModelInstance().getEstablishment("nome",str);
		this.onModelReceive(Establishment.class,es);	
	}
	/**
	 * recebimento assincrono
	 * @param c
	 * @param data
	 */
	public void onModelReceive(Class c, Object data) {
		final ListView listView = (ListView) findViewById(R.id.list_bar);			
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		List<Establishment> es = (List<Establishment>)data;
		this.adapter = new GenericAdapter(es, this);
		this.adapter.swapListeners(true);
		listView.setAdapter(this.adapter);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub		
	}
	/**
	 * fazer o adapter atualizar apos edicao
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if(this.keyword != null)
			this.configuration(this.keyword);
		ListView listView = ((ListView) findViewById(R.id.list_bar));
		if(listView.getAdapter() != null)
			((GenericAdapter)listView.getAdapter()).notifyDataSetChanged();
		
	};
	//evento do adapter
	@Override
	public void onFavoriteChange(Establishment e) {
		e.setFavorite(!e.isFavorite());
		this.getModelInstance().setEstablishmentFavorite(e);
		this.configuration(this.keyword);		
	}
	/**
	 * configuração de obtencao do modelo localizada
	 * @return
	 */
	private Model getModelInstance(){
		return AbstractModelFactory.getInstance("real");
	}
	

}
