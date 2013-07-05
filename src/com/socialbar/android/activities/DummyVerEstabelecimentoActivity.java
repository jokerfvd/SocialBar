package com.socialbar.android.activities;

import java.net.URI;
import java.util.Arrays;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.socialbar.android.R;
import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestClient;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;
import com.socialbar.android.rest.service.ServiceHelper;
import com.socialbar.android.rest.util.Logger;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DummyVerEstabelecimentoActivity extends Activity {
	private static final String TAG = DummyMainActivity.class.getSimpleName();
	
	private Long requestId;
	private BroadcastReceiver requestReceiver;
    private ServiceHelper mServiceHelper;
    private String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_activity_ver_estabelecimento);

/*
		// adicionando permissão para atividade network na main
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
*/
		Intent intent = getIntent();
		mId = intent.getStringExtra("ID");
		
		mServiceHelper = ServiceHelper.getInstance(this.getApplicationContext());
		requestId = mServiceHelper.getEstabelecimento(mId);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				long resultRequestId = intent
						.getLongExtra(ServiceHelper.EXTRA_REQUEST_ID, 0);

				if (resultRequestId == requestId) {

					int resultCode = intent.getIntExtra(ServiceHelper.EXTRA_RESULT_CODE, 0);

					if (resultCode == 200) {

						preencheDados();
					} else {
					}
				} else {
					Logger.debug(TAG, "Result is NOT for our request ID");
				}

			}
		};

		mServiceHelper = ServiceHelper.getInstance(this);
		this.registerReceiver(requestReceiver, filter);

		if (requestId == null) {
		} else if (mServiceHelper.isRequestPending(requestId)) {
		} else {
			preencheDados();
		}	
	}
	
	private void preencheDados() {
		Cursor cursor = getContentResolver().query(EstabelecimentosConstants.CONTENT_URI, null, 
				EstabelecimentosConstants.TID+" = "+mId, null, null);

		if (cursor.moveToFirst()) {
			EditText nome = (EditText) findViewById(R.id.nome);
		    int index = cursor.getColumnIndexOrThrow(EstabelecimentosConstants.NOME);
			nome.setText(cursor.getString(index));
			EditText endereco = (EditText) findViewById(R.id.endereco);
			index = cursor.getColumnIndexOrThrow(EstabelecimentosConstants.ENDERECO);
			endereco.setText(cursor.getString(index));
			EditText telefone = (EditText) findViewById(R.id.telefone);
			index = cursor.getColumnIndexOrThrow(EstabelecimentosConstants.TELEFONE);
			telefone.setText(cursor.getString(index));
			EditText gostei = (EditText) findViewById(R.id.gostei);
			index = cursor.getColumnIndexOrThrow(EstabelecimentosConstants.GOSTEI);
			gostei.setText(cursor.getString(index));
		}
		cursor.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_ver_estabelecimento, menu);
		return true;
	}

	public void EditarEstabelecimento(View view) throws JSONException {
		System.out.println("EditarEstabelecimento");

		EditText idEstabelecimento = (EditText) findViewById(R.id.idEstabelecimento);

		URI uri = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/"
						+ idEstabelecimento.getText() + ".json");

		JSONStringer json = new JSONStringer().object().key("estabelecimento")
				.object().key("nome")
				.value(((EditText) findViewById(R.id.nome)).getText())
				.key("endereco")
				.value(((EditText) findViewById(R.id.endereco)).getText())
				.key("telefone")
				.value(((EditText) findViewById(R.id.telefone)).getText())
				.key("gostei")
				.value(((EditText) findViewById(R.id.gostei)).getText())
				.endObject().endObject();

		Request request = new Request(Method.PUT, uri, null, json.toString()
				.getBytes());
		request.addHeader("Content-Type", Arrays.asList("application/json"));
		RestClient client = new RestClient();
		Response response = client.execute(request);
		View mensagem = findViewById(R.id.mensagem);
		mensagem.setVisibility(View.VISIBLE);

		if (response.status == 204)// NO CONTENT
		{
			System.out.println(new String(response.body));

			((EditText) mensagem)
					.setText("Estabelecimento editado com sucesso!");
		} else {
			((EditText) mensagem)
					.setText("O estabelecimento não pode ser editado!");
		}

	}

	public void DeletarEstabelecimento(View view) {
		System.out.println("DeletarEstabelecimento");
		EditText idEstabelecimento = (EditText) findViewById(R.id.idEstabelecimento);
		URI uri = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos/"
						+ idEstabelecimento.getText());
		Request request = new Request(Method.DELETE, uri, null, null);
		RestClient client = new RestClient();
		Response response = client.execute(request);
		View mensagem = findViewById(R.id.mensagem);
		mensagem.setVisibility(View.VISIBLE);
		if (response.status == 200) {
			((EditText) mensagem)
					.setText("Estabelecimento deletado com sucesso!");
			finish();
		} else {
			((EditText) mensagem)
					.setText("O estabelecimento não pode ser deletado!");
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		// Unregister for broadcast
		if (requestReceiver != null) {
			try {
				this.unregisterReceiver(requestReceiver);
			} catch (IllegalArgumentException e) {
				Logger.error(TAG, e.getLocalizedMessage(), e);
			}
		}
	}

}
