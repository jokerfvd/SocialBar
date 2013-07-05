package com.socialbar.android.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.socialbar.android.rest.provider.EstabelecimentosConstants;
import com.socialbar.android.rest.resource.Estabelecimento;
import com.socialbar.android.rest.rest.RestMethod;
import com.socialbar.android.rest.rest.RestMethodFactory;
import com.socialbar.android.rest.rest.RestMethodResult;
import com.socialbar.android.rest.rest.RestMethodFactory.Method;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

class EstabelecimentoProcessor {

	protected static final String TAG = EstabelecimentoProcessor.class
			.getSimpleName();

	private Context mContext;
	private byte[] mBody;
	private Map<String, List<String>>  mHeader;

	public EstabelecimentoProcessor(Context context, Map<String, List<String>>  header, byte[] body) {
		mContext = context;
		mHeader = header;
		mBody = body;
	}

	void getEstabelecimento(ProcessorCallback callback) {
		@SuppressWarnings("unchecked")
		RestMethod<Estabelecimento> getRestMethod = RestMethodFactory
				.getInstance(mContext).getRestMethod(
						EstabelecimentosConstants.CONTENT_URI, Method.GET,
						mHeader, mBody);
		RestMethodResult<Estabelecimento> result = getRestMethod.execute();
		updateContentProvider(result);
		callback.send(result.getStatusCode());

	}
	
	void removeEstabelecimento(ProcessorCallback callback) {
		@SuppressWarnings("unchecked")
		RestMethod<Estabelecimento> deleteRestMethod = RestMethodFactory
				.getInstance(mContext).getRestMethod(
						EstabelecimentosConstants.CONTENT_URI, Method.DELETE,
						mHeader, mBody);
		RestMethodResult<Estabelecimento> result = deleteRestMethod.execute();
		updateContentProvider(result);
		callback.send(result.getStatusCode());

	}
	
	void addEstabelecimento(ProcessorCallback callback) {
		@SuppressWarnings("unchecked")
		RestMethod<Estabelecimento> postRestMethod = RestMethodFactory
				.getInstance(mContext).getRestMethod(
						EstabelecimentosConstants.CONTENT_URI, Method.POST,
						mHeader, mBody);
		RestMethodResult<Estabelecimento> result = postRestMethod.execute();
		updateContentProvider(result);
		callback.send(result.getStatusCode());

	}
	
	void editEstabelecimento(ProcessorCallback callback) {
		@SuppressWarnings("unchecked")
		RestMethod<Estabelecimento> putRestMethod = RestMethodFactory
				.getInstance(mContext).getRestMethod(
						EstabelecimentosConstants.CONTENT_URI, Method.PUT,
						mHeader, mBody);
		RestMethodResult<Estabelecimento> result = putRestMethod.execute();
		updateContentProvider(result);
		callback.send(result.getStatusCode());

	}

	private void updateContentProvider(RestMethodResult<Estabelecimento> result) {

		if (result != null && result.getResource() != null) {

			Estabelecimento resource = result.getResource();

			ContentValues values = new ContentValues();
			values.put(EstabelecimentosConstants.NOME, resource.getNome());
			values.put(EstabelecimentosConstants.ENDERECO,
					resource.getEndereco());
			values.put(EstabelecimentosConstants.TELEFONE,
					resource.getTelefone());
			values.put(EstabelecimentosConstants.GOSTEI, resource.getGostei());
			values.put(EstabelecimentosConstants.LATITUDE,
					resource.getLatitude());
			values.put(EstabelecimentosConstants.LONGITUDE,
					resource.getLatitude());
			values.put(EstabelecimentosConstants.TID,
					resource.getId());

			Cursor cursor = mContext.getContentResolver().query(
					EstabelecimentosConstants.CONTENT_URI, null, EstabelecimentosConstants.TID+" = "+resource.getId(), null,
					null);
			if (cursor.moveToFirst()) {
				int id = cursor.getInt(cursor
						.getColumnIndexOrThrow(BaseColumns._ID));
				mContext.getContentResolver().update(
						ContentUris.withAppendedId(
								EstabelecimentosConstants.CONTENT_URI, id),
						values, null, null);
			} else {
				mContext.getContentResolver().insert(
						EstabelecimentosConstants.CONTENT_URI, values);
			}
			cursor.close();
		}

	}

}
