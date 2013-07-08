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
import android.widget.TextView;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.dummy.FactoryDummy;

public class SearchActivity extends Activity implements OnClickListener {
	private GenericActivity genericActivity;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

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
		case R.id.menu_send:
			if (this.sendData())
				this.onBackPressed();
			return true;
		case R.id.menu_cancel:
			this.onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean sendData() {

		String name = ((EditText) findViewById(R.id.edit_name)).getText()
				.toString();

		if (name.length() < 2)
			return false;

		Model model = AbstractModelFactory.getInstance("dummy");
		((FactoryDummy) model).save();
		List<Establishment> es = model.getEstablishment(name,name);
		
		((TextView) findViewById(R.id.text_name)).setText("encontrados: "+String.valueOf(es.size()));
		// grava dados no arquivo
		return false;

	}

	@Override
	public void onBackPressed() {
		this.genericActivity.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_bar_edit, menu);
		return true;
	}

}
