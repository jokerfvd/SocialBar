package com.socialbar.android.model.provider;

import org.json.JSONException;
import org.json.JSONStringer;

import com.socialbar.android.R;
import com.socialbar.android.activities.DummyMainActivity;

import com.socialbar.android.model.ModelEvent;
import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.service.ServiceHelper;
import com.socialbar.android.rest.util.Logger;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ProviderActivity {

	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			long resultId = intent.getLongExtra(ServiceHelper.EXTRA_REQUEST_ID,
					0);
			int resultCode = intent.getIntExtra(
					ServiceHelper.EXTRA_RESULT_CODE, 0);

			if (resultId == requestId) {

				if (resultCode == 200) {

					preencheDados();
				}
			} else {
				Logger.debug(TAG, "Result is NOT for our request ID");
			}

		}
	}

	private static final String TAG = DummyMainActivity.class.getSimpleName();

	private Long requestId;
	private BroadcastReceiver requestReceiver;
	private ServiceHelper mServiceHelper;
	private String mId;
	private ModelEvent modelEvent;

	public ProviderActivity(ModelEvent me) {
		modelEvent = me;
	}

	public BroadcastReceiver getEstablishment(String id) {
		mId = "52";
		Activity activity = (Activity) modelEvent;
		mServiceHelper = ServiceHelper.getInstance(activity
				.getApplicationContext());
		requestId = mServiceHelper.getEstabelecimento(mId);

		IntentFilter filter = new IntentFilter(
				ServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new MyReceiver();
		activity.registerReceiver(requestReceiver, filter);

		return requestReceiver;
	}

	private void preencheDados() {
		EstablishmentProvider es = new EstablishmentProvider(mId);

		Activity activity = (Activity) modelEvent;
		Cursor cursor = activity.getContentResolver().query(
				EstabelecimentosConstants.CONTENT_URI, null,
				EstabelecimentosConstants.TID + " = " + mId, null, null);

		if (cursor.moveToFirst()) {
			int index = cursor
					.getColumnIndexOrThrow(EstabelecimentosConstants.NOME);
			es.setName(cursor.getString(index));
			index = cursor
					.getColumnIndexOrThrow(EstabelecimentosConstants.ENDERECO);
			es.setAddress(cursor.getString(index));
			index = cursor
					.getColumnIndexOrThrow(EstabelecimentosConstants.TELEFONE);
			es.setPhoneNumber(cursor.getString(index));

			es.setLatitude(0);
			es.setLongitude(0);
			es.setLastModified(System.currentTimeMillis());
			es.setFavorite(true);
			modelEvent.onModelReceive(EstablishmentProvider.class, es);
		}
		cursor.close();
		// activity.unregisterReceiver(editReceiver);
	}

}
