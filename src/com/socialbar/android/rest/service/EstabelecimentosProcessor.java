package com.socialbar.android.rest.service;

import static com.socialbar.android.rest.provider.EstabelecimentosConstants.AUTHORITY;

import java.util.ArrayList;

import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.provider.EstabelecimentosProvider;
import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.resource.Estabelecimentos;
import com.socialbar.android.rest.rest.RestMethod;
import com.socialbar.android.rest.rest.RestMethodFactory;
import com.socialbar.android.rest.rest.RestMethodResult;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.provider.BaseColumns;


class EstabelecimentosProcessor {

	protected static final String TAG = EstabelecimentosProcessor.class.getSimpleName();

	private Context mContext;

	public EstabelecimentosProcessor(Context context) {
		mContext = context;
	}

	void getEstabelecimentos(ProcessorCallback callback) {

		// (4) Insert-Update the ContentProvider with a status column and
		// results column
		// Look at ContentProvider example, and build a content provider
		// that tracks the necessary data.

		// (5) Call the REST method
		// Create a RESTMethod class that knows how to assemble the URL,
		// and performs the HTTP operation.

		@SuppressWarnings("unchecked")
		RestMethod<Estabelecimentos> getRestMethod = RestMethodFactory.getInstance(mContext).getRestMethod(
				EstabelecimentosConstants.CONTENT_URI, Method.GET, null, null);
		RestMethodResult<Estabelecimentos> result = getRestMethod.execute();

		/*
		 * (8) Insert-Update the ContentProvider status, and insert the result
		 * on success Parsing the JSON response (on success) and inserting into
		 * the content provider
		 */

		resetContentProvider(); // ??
		updateContentProvider(result);

		// (9) Operation complete callback to Service

		callback.send(result.getStatusCode());

	}
	
	private void resetContentProvider(){
		ContentResolver resolver = mContext.getContentResolver();
		ContentProviderClient client = resolver.acquireContentProviderClient(EstabelecimentosConstants.CONTENT_URI);
		EstabelecimentosProvider provider = (EstabelecimentosProvider) client.getLocalContentProvider();
		provider.resetDatabase();
		client.release();
	}

	
	private void updateContentProvider(RestMethodResult<Estabelecimentos> result) {

		if (result != null && result.getResource() != null) {

			ArrayList<Estabelecimento> lista = result.getResource().getLista();
			Cursor cursor = mContext.getContentResolver().query(EstabelecimentosConstants.CONTENT_URI,
					null, null, null, null);
			for(int i=0; i < lista.size(); i++){
				Estabelecimento estabelecimento = lista.get(i);		

				ContentValues values = new ContentValues();
				values.put(EstabelecimentosConstants.NOME, estabelecimento.getNome());
				values.put(EstabelecimentosConstants.ENDERECO, estabelecimento.getEndereco());
				values.put(EstabelecimentosConstants.TELEFONE, estabelecimento.getTelefone());
				values.put(EstabelecimentosConstants.GOSTEI, estabelecimento.getGostei());
				values.put(EstabelecimentosConstants.LATITUDE, estabelecimento.getLatitude());
				values.put(EstabelecimentosConstants.LONGITUDE, estabelecimento.getLatitude());
				values.put(EstabelecimentosConstants.TID, estabelecimento.getId());
				if (cursor.moveToFirst()) {
					int id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID));
					mContext.getContentResolver().update(
							ContentUris.withAppendedId(EstabelecimentosConstants.CONTENT_URI, id), values,
							null, null);
				}
				else{
					mContext.getContentResolver().insert(EstabelecimentosConstants.CONTENT_URI, values);
				}
			}
			cursor.close();
		}

	}

}
