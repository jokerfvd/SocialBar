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

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DummyCriarEstabelecimentoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_activity_criar_estabelecimento);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_criar_estabelecimento, menu);
		return true;
	}

	public void criar(View view) {
		URI uri = URI
				.create("http://restserveruff.herokuapp.com/estabelecimentos");

		try {

			JSONStringer json = new JSONStringer()
					.object()
					.key("estabelecimento")
					.object()
					.key("nome")
					.value(((EditText) findViewById(R.id.EditText04)).getText())
					.key("endereco")
					.value(((EditText) findViewById(R.id.EditText01)).getText())
					.key("telefone")
					.value(((EditText) findViewById(R.id.EditText02)).getText())
					.key("rank")
					.value(((EditText) findViewById(R.id.EditText03)).getText())
					.endObject().endObject();

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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
