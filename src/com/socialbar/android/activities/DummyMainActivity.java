package com.socialbar.android.activities;

import java.net.URI;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.socialbar.android.R;
import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.provider.ResourceTable;
import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.resource.Estabelecimentos;
import com.socialbar.android.rest.rest.GetEstabelecimentosRestMethod;
import com.socialbar.android.rest.rest.Request;
import com.socialbar.android.rest.rest.Response;
import com.socialbar.android.rest.rest.RestClient;
import com.socialbar.android.rest.rest.RestMethodResult;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;
import com.socialbar.android.rest.service.ServiceHelper;
import com.socialbar.android.rest.util.Logger;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;


public class DummyMainActivity extends ListActivity {
	private static final String TAG = DummyMainActivity.class.getSimpleName();
	
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING STRING ADAPTER WHICH WILL HANDLE DATA OF LISTVIEW
    ArrayAdapter<String> adapter;
    
    private Long requestId;
	private BroadcastReceiver requestReceiver;
    private ServiceHelper mServiceHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_activity_main);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
	    setListAdapter(adapter);
	    
	    mServiceHelper = ServiceHelper.getInstance(this.getApplicationContext());
	    
	    //monitorando o click no listView
	    getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	Intent intent = new Intent(DummyMainActivity.this, DummyVerEstabelecimentoActivity.class);
                intent.putExtra("ID", parent.getItemAtPosition(position).toString());
                startActivity(intent);      
			}
		});
			    				
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

				Logger.debug(TAG, "Received intent " + intent.getAction() + ", request ID "
						+ resultRequestId);

				if (resultRequestId == requestId) {

					Logger.debug(TAG, "Result is for our request ID");

					int resultCode = intent.getIntExtra(ServiceHelper.EXTRA_RESULT_CODE, 0);

					Logger.debug(TAG, "Result code = " + resultCode);

					if (resultCode == 200) {

						Logger.debug(TAG, "Updating UI with new data");

						atualizaEstabelecimentos();
						//showNameToast(name);

					} else {
						//showToast(getString(R.string.error_occurred));
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
			///setRefreshing(true);
		} else {
			//setRefreshing(false);
			atualizaEstabelecimentos();
			//showNameToast(name);
		}	
	}
	
	
	private void atualizaEstabelecimentos() {
		Cursor cursor = getContentResolver().query(EstabelecimentosConstants.CONTENT_URI, null, null, null, null);

		int num = cursor.getCount();
		while (cursor.moveToNext()) {
		    int index = cursor.getColumnIndexOrThrow(EstabelecimentosConstants.NOME);
			String nome = cursor.getString(index);
			listItems.add(nome);
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_main, menu);
		return true;
	}
	
	public void listarEstabelecimentos(View view){
		listItems.clear();
		adapter.notifyDataSetChanged();
		requestId = mServiceHelper.getEstabelecimentos();
    }
	
	public void criarEstabelecimento(View view) {
		Intent intent = new Intent(DummyMainActivity.this, DummyCriarEstabelecimentoActivity.class);
		int requestCode = 1; // Or some number you choose
		startActivityForResult(intent,requestCode); 
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
