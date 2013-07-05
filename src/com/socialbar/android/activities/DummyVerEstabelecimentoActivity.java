package com.socialbar.android.activities;

import org.json.JSONException;
import org.json.JSONStringer;

import com.socialbar.android.R;
import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.service.ServiceHelper;
import com.socialbar.android.rest.util.Logger;

import android.os.Bundle;
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
	public class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			long resultId = intent
					.getLongExtra(ServiceHelper.EXTRA_REQUEST_ID, 0);
			int resultCode = intent.getIntExtra(ServiceHelper.EXTRA_RESULT_CODE, 0);

			if (resultId == requestId) {

				if (resultCode == 200) {

					preencheDados();
				} else {
				}
			}else if ((deleteId != null) && resultId == deleteId) {
				View mensagem = findViewById(R.id.mensagem);
				mensagem.setVisibility(View.VISIBLE);
				//TODO descobrir pq ta vindo 506
				if (resultCode == 200 || resultCode == 506) {
					((EditText) mensagem)
							.setText("Estabelecimento deletado com sucesso!");
					finish();
				} else {
					((EditText) mensagem)
							.setText("O estabelecimento não pode ser deletado!");
				}
			}else if ((editedId != null) && resultId == editedId) {
				View mensagem = findViewById(R.id.mensagem);
				mensagem.setVisibility(View.VISIBLE);

				//TODO descobrir pq ta vindo 506
				if (resultCode == 204 | resultCode == 506)// NO CONTENT
				{
					((EditText) mensagem)
							.setText("Estabelecimento editado com sucesso!");
					finish();
				} else {
					((EditText) mensagem)
							.setText("O estabelecimento não pode ser editado!");
				}
			}
			else {
				Logger.debug(TAG, "Result is NOT for our request ID");
			}

		}
		} 
	
	
	private static final String TAG = DummyMainActivity.class.getSimpleName();
	
	private Long requestId, deleteId, editedId;
	private BroadcastReceiver requestReceiver,deleteReceiver,editReceiver;
    private ServiceHelper mServiceHelper;
    private String mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_activity_ver_estabelecimento);

		Intent intent = getIntent();
		mId = intent.getStringExtra("ID");
		
		mServiceHelper = ServiceHelper.getInstance(this.getApplicationContext());
		requestId = mServiceHelper.getEstabelecimento(mId);
		
		IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
		requestReceiver = new MyReceiver();
		this.registerReceiver(requestReceiver, filter);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (requestId == null) {
		} else if (mServiceHelper.isRequestPending(requestId)) {
		} else {
			preencheDados();
		}
		
		if (deleteId == null) {
		} else if (mServiceHelper.isRequestPending(deleteId)) {
		} 
		else{
			//finish();
		}
		
		if (editedId == null) {
		} else if (mServiceHelper.isRequestPending(editedId)) {
		} 
		else{
			//finish();
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
			String aux = cursor.getString(index);
			gostei.setText(aux);
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
		JSONStringer json = new JSONStringer()
		.object()
		.key("estabelecimento")
		.object()
		.key("nome")
		.value(((EditText) findViewById(R.id.nome)).getText())
		.key("endereco")
		.value(((EditText) findViewById(R.id.endereco)).getText())
		.key("telefone")
		.value(((EditText) findViewById(R.id.telefone)).getText())
		.key("gostei")
		.value(((EditText) findViewById(R.id.gostei)).getText())
		.endObject().endObject();
		
		editedId = mServiceHelper.editEstabelecimento(json.toString() ,mId);
		IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
		editReceiver = new MyReceiver();
		this.registerReceiver(editReceiver, filter);

	}

	public void DeletarEstabelecimento(View view) {
		deleteId = mServiceHelper.removeEstabelecimento(mId);
		IntentFilter filter = new IntentFilter(ServiceHelper.ACTION_REQUEST_RESULT);
		deleteReceiver = new MyReceiver();
		this.registerReceiver(deleteReceiver, filter);
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
		if (deleteReceiver != null) {
			try {
				this.unregisterReceiver(deleteReceiver);
			} catch (IllegalArgumentException e) {
				Logger.error(TAG, e.getLocalizedMessage(), e);
			}
		}
		if (editReceiver != null) {
			try {
				this.unregisterReceiver(editReceiver);
			} catch (IllegalArgumentException e) {
				Logger.error(TAG, e.getLocalizedMessage(), e);
			}
		}
	}

}
