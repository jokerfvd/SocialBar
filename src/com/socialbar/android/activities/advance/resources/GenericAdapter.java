package com.socialbar.android.activities.advance.resources;

import java.util.List;

import com.socialbar.android.R;
import com.socialbar.android.activities.BarProfileActivity;
import com.socialbar.android.model.AbstractModelFactory;
import com.socialbar.android.model.Establishment;
import com.socialbar.android.model.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.socialbar.android.model.dummy.FactoryDummy;
/**
 * Class <code>Adaptador generico para listview</code>.
 */
public class GenericAdapter extends BaseAdapter {

	private List<Establishment> data;
	private Context context;
	
	/**
	 * construtor que recebe dados e o contexto da aplicacao
	 * @param data
	 * @param c
	 */
	public GenericAdapter(List<Establishment> data, Context c) {
		this.data = data;
		this.context = c;
		Log.i("Generic ADAPTER", "constructor " + String.valueOf(data.size()));
	}
	/**
	 * metodo para alternar acoes entre eventos
	 * @param swap
	 */
	//metodos da inface BaseAdapter
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	/**
	 * metodo que contem a organizacao da exibicao dos dados na tela
	 * eh uma exibicao personalizada
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.snippet_bar_list_item, parent, false);
			holder = new Holder();
			holder.logo = (ImageView) v.findViewById(R.id.img_logo);
			holder.name = (TextView) v.findViewById(R.id.text_name);
			holder.address = (TextView) v.findViewById(R.id.text_address);
			holder.favorite = (ImageView) v.findViewById(R.id.img_favorite);
			
			
			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}

		Establishment e = this.data.get(position);
		
		if (e.isFavorite())
				holder.favorite.setImageResource(R.drawable.icon_rating_important);
			else
				holder.favorite.setImageResource(R.drawable.icon_rating_not_important);
		
		holder.logo.setImageResource(R.drawable.bar_logo);
		holder.name.setText(e.getName());
		holder.address.setText(e.getAddress());

		//evento de click no item
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View vis) {
				Establishment e = data.get(position);				
				goToProfile(e);
			}
		});
		//evento de longclick no item
		v.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				final Establishment e = data.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				
				String dialog = context.getString(R.string.dialog_favorites);
				
				builder.setTitle(e.getName())
						.setMessage(dialog)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(R.string.dialog_yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
											setFavorite(e);
									}
								}).setIcon(android.R.drawable.ic_menu_view)
						.setNegativeButton(R.string.dialog_not, null).show();
				return false;
			}
		});
		return v;
	}
	
	/**
	 * metodo para ir para a tela do perfil do bar
	 * @param e
	 */
	private void goToProfile(Establishment e){
		Intent intent = new Intent(context,	BarProfileActivity.class);
		intent.putExtra("ID", e.getID());
		context.startActivity(intent);
	}
	/**
	 * metodo para realizar a mudanca de favorito
	 * @param holder
	 * @param e
	 * @param change
	 */
	private void setFavorite(Establishment e) {
		((GenericAdapterEvent)context).onFavoriteChange(e);
	}
	/**
	 * class static para manter elementos da view
	 * metodo de atribuicao de melhor desempenho
	 * @author Felipe
	 *
	 */
	static class Holder {
		public ImageView favorite;
		public ImageView logo;
		public TextView name;
		public TextView address;

	}
	/**
	 * configuração de obtencao do modelo localizada
	 * @return
	 */
	private Model getModelInstance(){
		return AbstractModelFactory.getInstance("real");
	}
}
