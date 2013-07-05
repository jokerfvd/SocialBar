package com.socialbar.android.activities.advance.resources;

import java.nio.Buffer;
import java.util.List;

import com.google.android.gms.plus.model.people.Person.Image;
import com.socialbar.android.R;
import com.socialbar.android.activities.BarProfileActivity;
import com.socialbar.android.model.Establishment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.socialbar.android.model.Establishment;

public class GenericAdapter extends BaseAdapter {

	private List<Establishment> data;
	private Context context;

	public GenericAdapter(List<Establishment> data, Context c) {
		this.data = data;
		this.context = c;
		Log.i("Generic ADAPTER", "constructor " + String.valueOf(data.size()));
	}

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i("Generic ADAPTER", "getView");
		Holder holder;
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.snippet_bar_list_item, parent, false);
			holder = new Holder();
			holder.logo = (ImageView) v.findViewById(R.id.img_logo);
			holder.name = (TextView) v.findViewById(R.id.text_name);
			holder.address = (TextView) v.findViewById(R.id.text_address);
			holder.favorite = (ImageButton) v.findViewById(R.id.btn_favorite);

			v.setTag(holder);
		} else {
			holder = (Holder) v.getTag();
		}
		
		Establishment e = this.data.get(position);
		holder.logo.setImageResource(R.drawable.logo);
		holder.name.setText(e.getName());
		holder.address.setText(e.getAddress());
		
		holder.favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View vis) {
				Establishment e = data.get(position);
				Toast.makeText(context,"favoritar id "+e.getID(),Toast.LENGTH_LONG ).show();
				Log.i("Click btn favorite", "clicked");
			}
		});
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View vis) {

				Establishment e = data.get(position);
				Intent intent = new Intent(context, BarProfileActivity.class);
				intent.putExtra("ID", e.getID());
				context.startActivity(intent);
			}
		});

		return v;
	}

	static class Holder {
		public ImageButton favorite;
		public ImageView logo;
		public TextView name;
		public TextView address;

	}
}
