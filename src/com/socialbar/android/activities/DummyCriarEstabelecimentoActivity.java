package com.socialbar.android.activities;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;


import org.json.JSONException;
import org.json.JSONStringer;

import com.socialbar.android.R;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestClient;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;
import com.socialbar.android.rest.service.ServiceHelper;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		//URI uri = URI.create("http://restserveruff.herokuapp.com/estabelecimentos");

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
/* MANEIRA ERRADA
			Request request = new Request(Method.POST, uri, null, json.toString().getBytes());
			RestClient client = new RestClient();
			request.addHeader("Content-Type", Arrays.asList("application/json"));
			Response response = client.execute(request);

			if (response.status == 200) {
				Toast.makeText(getBaseContext(), "okkk", Toast.LENGTH_LONG);
				setResult(RESULT_OK, null);
				finish();
			} else {
				setResult(RESULT_CANCELED, null);
				finish();
			}
*/			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
