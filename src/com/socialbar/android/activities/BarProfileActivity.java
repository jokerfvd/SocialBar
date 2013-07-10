package com.socialbar.android.activities;

import java.sql.Date;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.socialbar.android.R;
import com.socialbar.android.activities.advance.resources.GenericActivity;
import com.socialbar.android.activities.advance.resources.GenericActivitySlider;
import com.socialbar.android.activities.advance.resources.Moeda;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Feature;
import com.socialbar.android.model.Model;
import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.model.Product;
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

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * Activity <code>Perfil do estabelecimento</code>.
 */
public class BarProfileActivity extends Activity implements OnClickListener,
		ModelEvent {
	/**
	 * Funcoes genericas utilizadas por um conjunto de activities
	 */
	private GenericActivity genericActivity;
	private BroadcastReceiver broadCastReceiver;
	private Establishment establishment;

	/**
	 * Called when the activity is first created.
	 * */
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
		this.genericActivity.strictMode();		

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
	 * 
	 * @param id
	 */
	private void setEstablishment(String id) {
		// Model model = AbstractModelFactory.getInstance();
		// broadCastReceiver = model.getEstablishmentPrototype(this, id);
		Model model = AbstractModelFactory.getInstance("real");
		Establishment es = model.getEstablishment(id);
		this.onModelReceive(Establishment.class, es);
	}

	/**
	 * metodo para recebimento de mensagem para carregamento assincrono
	 */
	@Override
	public void onModelReceive(@SuppressWarnings("rawtypes") Class c,
			Object data) {

		this.establishment = (Establishment) data;
		this.exec();
	}

	/**
	 * execucao dos dados para a interface
	 */
	private void exec() {
		SimpleDateFormat dfmt = new SimpleDateFormat("EEEE, d MMMM yyyy",
				Locale.getDefault());
		Date date = new Date(this.establishment.getLastModified());

		((TextView) findViewById(R.id.text_name)).setText(this.establishment
				.getName());
		((TextView) findViewById(R.id.text_people))
				.setText(getString(R.string.bar_population_symbol)
						+ String.valueOf(this.establishment.getPeople()));
		((TextView) findViewById(R.id.text_last_modified))
				.setText(getString(R.string.bar_last_modified) + " "
						+ dfmt.format(date));
		((TextView) findViewById(R.id.text_address)).setText(this.establishment
				.getAddress());

		((TextView) findViewById(R.id.text_address)).setText(this.establishment
				.getAddress());
		((TextView) findViewById(R.id.text_phone)).setText(this.establishment
				.getPhoneNumber());
		((TextView) findViewById(R.id.text_products_content)).setText(this
				.compileProducts(this.establishment.getProducts()));
		((TextView) findViewById(R.id.text_features_content)).setText(this
				.compileFeatures(this.establishment.getFeatures()));

		// botao
		ImageButton favorite = (ImageButton) findViewById(R.id.btn_favorite);
		favorite.setOnClickListener(this);
		this.setFavoriteImage(favorite);
	}

	/**
	 * funcoes dos botoes
	 */
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
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.box_description);

		int size = linearLayout.getHeight();

		if (expanded == 0)// seta o valor do XML
			expanded = size;

		Toast.makeText(v.getContext(), String.valueOf(size), Toast.LENGTH_SHORT)
				.show();

		LayoutParams params;

		if (expanded == size) {

			// Setar novo texto para o botao
			bt.setText(v.getContext().getResources()
					.getString(R.string.button_expandable_less));

			// Setar parametros para a aumentar o tamanho
			params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 1f);

		} else {
			bt.setText(v.getContext().getResources()
					.getString(R.string.button_expandable_more));
			params = new LayoutParams(LayoutParams.MATCH_PARENT, expanded, 1f);
		}

		linearLayout.setLayoutParams(params);// aplicar novo tamanho
	}

	/**
	 * metodo para setar a estrela do favorito
	 * 
	 * @param favorite
	 */
	private void setFavoriteImage(ImageButton favorite) {
		if (this.establishment.isFavorite())
			favorite.setImageResource(R.drawable.icon_rating_important);
		else
			favorite.setImageResource(R.drawable.icon_rating_not_important);
	}

	/**
	 * metodo para mudar estado do modelo
	 * 
	 * @param v
	 */
	private void changeFavorite(View v) {
		this.establishment.setFavorite(!this.establishment.isFavorite());
		this.setFavoriteImage((ImageButton) v);
		//salvar storage
		Model model = AbstractModelFactory.getInstance("dummy");
		((FactoryDummy) model).save();
		//exibir alerta
		Toast.makeText(
				this,
				getString(R.string.dialog_favorite_state_saved)
						+ this.establishment.getName(), Toast.LENGTH_SHORT)
				.show();
	}

	/**
	 * criação de icones da actionbar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			this.exec();
			return true;
		case R.id.menu_edit:
			Intent intent = new Intent(this, EditBarActivity.class);
			intent.putExtra("ID", this.establishment.getID());
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

	@Override
	protected void onPause() {
		super.onPause();
		// remove broadcast
		if (broadCastReceiver != null) {
			try {
				this.unregisterReceiver(broadCastReceiver);
			} catch (IllegalArgumentException e) {
				Log.i("onPause", e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * Prepara string de produtos para serem exibidas
	 * 
	 * @param products
	 * @return
	 */
	private String compileProducts(List<Product> products) {
		StringBuilder sb = new StringBuilder();
		if (products != null) {
			for (Product p : products) {
				if (sb.length() > 0)
					sb.append(System.getProperty("line.separator"));
				sb.append(p.getName());
				sb.append(": ");
				sb.append(Moeda.mascaraDinheiro(p.getPrice(),
						Moeda.DINHEIRO_REAL));
			}
		}
		return sb.toString();
	}

	/**
	 * Prepara string de caracteristicas para serem exibidas
	 * 
	 * @param features
	 * @return
	 */
	private String compileFeatures(List<Feature> features) {
		StringBuilder sb = new StringBuilder();
		if (features != null) {
			for (Feature f : features) {
				if (sb.length() > 0)
					sb.append(System.getProperty("line.separator"));
				sb.append(f.getName());

			}
		}

		return sb.toString();
	}

}
