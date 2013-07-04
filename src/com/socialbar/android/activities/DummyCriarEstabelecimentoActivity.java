package com.socialbar.android.activities;

import org.json.JSONException;
import org.json.JSONStringer;

import com.socialbar.android.R;
import com.socialbar.android.rest.service.ServiceHelper;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class DummyCriarEstabelecimentoActivity extends Activity {
	
	private Long requestId;
	private BroadcastReceiver requestReceiver;
    private ServiceHelper mServiceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_activity_criar_estabelecimento);
		
		mServiceHelper = ServiceHelper.getInstance(this.getApplicationContext());
		
		//adicionando permissão para atividade network na main
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_criar_estabelecimento, menu);
		return true;
	}

	public void criar(View view) {
		try {
			
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
					.key("latitude")
					.value(((EditText) findViewById(R.id.latitude)).getText())
					.key("longitude")
					.value(((EditText) findViewById(R.id.longitude)).getText())
					.endObject().endObject();
			
			requestId = mServiceHelper.addEstabelecimento(json.toString());		
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
