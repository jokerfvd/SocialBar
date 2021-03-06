package com.socialbar.android.activities;

import android.app.ActionBar;
import android.app.Activity;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.dummy.FactoryDummy;

/**
 * Activity <code>Editar ou novo estabelecimento</code>.
 */
public class EditBarActivity extends Activity implements OnClickListener,
		LocationListener {
	private GenericActivity genericActivity;
	private Establishment establishment;
	LocationManager locationManager;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bar_edit);

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
		 * inicializa a configuração do oncreate
		 */

		this.configuration();
	}

	/**
	 * metodo basico de configuracao onCreate considera parametros passados
	 */
	private void configuration() {
		if (getIntent().hasExtra("ID")) {
			this.setEstablishment(getIntent().getExtras().getString("ID"));
		} else {
			this.setTitle(getResources().getString(R.string.activity_new_bar));
			this.setEstablishment(null);
		}
	}

	/**
	 * metodo para requisitar um estabelecimento, seja ele novo ou existente
	 * 
	 * @param id
	 */
	private void setEstablishment(String id) {
		Establishment es;
		if (id != null)
			es = this.getModelInstance().getEstablishment(id);
		else {
			es = this.getModelInstance().getEstablishment();
			this.prepareNewObject(es);
			this.getLocation();
		}
		this.onModelReceive(Establishment.class, es);

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

	@Override
	public void onBackPressed() {
		this.genericActivity.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_bar_edit, menu);
		return true;
	}

	/**
	 * preparar novo objeto para ser editado caso esteja nulo, lancara
	 * NullPointerException
	 * 
	 * @param e
	 */
	private void prepareNewObject(Establishment e) {
		e.setAddress("");
		e.setName("");
		e.setPhoneNumber("");
	}

	/**
	 * metodo para recebimento assincrono
	 * 
	 * @param c
	 * @param data
	 */
	public void onModelReceive(@SuppressWarnings("rawtypes") Class c,
			Object data) {
		Log.i("onModelReceive", "chegou");

		this.establishment = (Establishment) data;

		((EditText) findViewById(R.id.edit_name)).setText(this.establishment
				.getName());
		((EditText) findViewById(R.id.edit_address)).setText(this.establishment
				.getAddress());
		((EditText) findViewById(R.id.edit_phone)).setText(this.establishment
				.getPhoneNumber());

		// pegar latitude e longitude do sensor se for novo objeto
		if (this.establishment.getID() != null) {
			((EditText) findViewById(R.id.edit_latitude)).setText(String
					.valueOf(this.establishment.getLatitude()));
			((EditText) findViewById(R.id.edit_longitude)).setText(String
					.valueOf(this.establishment.getLongitude()));
		}

	}

	/**
	 * preparando data para envio, verifica campos vazios
	 * 
	 * @return
	 */
	private boolean sendData() {
		if (this.establishment != null) {

			String name = ((EditText) findViewById(R.id.edit_name)).getText()
					.toString();
			String address = ((EditText) findViewById(R.id.edit_address))
					.getText().toString();
			String phone = ((EditText) findViewById(R.id.edit_phone)).getText()
					.toString();
			String latitude = ((EditText) findViewById(R.id.edit_latitude))
					.getText().toString();
			String longitude = ((EditText) findViewById(R.id.edit_longitude))
					.getText().toString();

			if (name.length() < 2 && address.length() < 2)
				return false;

			this.establishment.setName(name);
			this.establishment.setAddress(address);
			this.establishment.setLatitude(Double.valueOf(latitude));
			this.establishment.setLongitude(Double.valueOf(longitude));
			this.establishment.setPhoneNumber(phone);
			this.establishment.setLastModified(System.currentTimeMillis());

			if (getIntent().hasExtra("ID"))
				this.getModelInstance().updateEstablishment(this.establishment);
			else
				this.getModelInstance().addEstablishment(this.establishment);

			return true;
		}
		return false;
	}

	/**
	 * metodo para setar para pegar a localização
	 */
	public void getLocation() {
		this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String best = this.locationManager.getBestProvider(criteria, true);
		Location location = this.locationManager.getLastKnownLocation(best);
		if (location != null)
			this.onLocationChanged(location);

	}

	/**
	 * disparado pelo evento quando a localizacao eh atualizada
	 */
	// metodo da interface location
	@Override
	public void onLocationChanged(Location location) {
		((EditText) findViewById(R.id.edit_latitude)).setText(String
				.valueOf(location.getLatitude()));
		((EditText) findViewById(R.id.edit_longitude)).setText(String
				.valueOf(location.getLongitude()));
		this.locationManager.removeUpdates(this);
	}

	// metodo da interface location
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	// metodo da interface location
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	// metodo da interface location
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/**
	 * configuração de obtencao do modelo localizada
	 * 
	 * @return
	 */
	private Model getModelInstance() {
		return AbstractModelFactory.getInstance("real");
	}

}
