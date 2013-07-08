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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.activities.advance.resources.GenericAdapter;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.dummy.FactoryDummy;

public class SearchActivity extends Activity implements OnClickListener,OnQueryTextListener {
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
		SearchView searchView = (SearchView) searchViewMenuItem.getActionView();
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		this.configuration("");
		return true;
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		this.configuration(newText);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		this.configuration(query);
		return true;
	}
	/**
	 * configuracao apos entrada de busca
	 * @param str
	 */
	private void configuration(String str) {
		Model model = AbstractModelFactory.getInstance("dummy");
		List<Establishment> es = model.getEstablishment(str,str);
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
		ListView listView = ((ListView) findViewById(R.id.list_bar));
		if(listView.getAdapter() != null)
			((GenericAdapter)listView.getAdapter()).notifyDataSetChanged();
		
	};

}
