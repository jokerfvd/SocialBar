package com.socialbar.android.rest.rest;

import java.util.List;
import java.util.Map;

import com.socialbar.android.rest.provider.EstabelecimentosConstants;


import android.content.UriMatcher;
import android.net.Uri;
import android.content.Context;

public class RestMethodFactory {

	private static RestMethodFactory instance;
	private static Object lock = new Object();
	private UriMatcher uriMatcher;
	private Context mContext;

	private static final int ESTABELECIMENTOS = 1;
	private static final int ESTABELECIMENTO_ID = 2;

	private RestMethodFactory(Context context) {
		mContext = context.getApplicationContext();
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(EstabelecimentosConstants.AUTHORITY, EstabelecimentosConstants.TABLE_NAME, ESTABELECIMENTOS);
		uriMatcher.addURI(EstabelecimentosConstants.AUTHORITY, EstabelecimentosConstants.TABLE_NAME+"/#", ESTABELECIMENTO_ID);
	}

	public static RestMethodFactory getInstance(Context context) {
		synchronized (lock) {
			if (instance == null) {
				instance = new RestMethodFactory(context);
			}
		}

		return instance;
	}

	public RestMethod getRestMethod(Uri resourceUri, Method method,
			Map<String, List<String>> headers, byte[] body) {

		switch (uriMatcher.match(resourceUri)) {
		case ESTABELECIMENTOS:
			if (method == Method.GET) {
				if (headers != null)
					return new GetEstabelecimentoRestMethod(mContext, headers);
				else
					return new GetEstabelecimentosRestMethod(mContext);
			}
			else if (method == Method.POST) {
				return new PostEstabelecimentoRestMethod(mContext, body);
			}
			break;
		case ESTABELECIMENTO_ID:
			if (method == Method.GET) {
				return new GetEstabelecimentoRestMethod(mContext, headers);
			}
			/*
			else if (method == Method.POST) {
				return new PostEstabelecimentoRestMethod(mContext, body);
			}
			*/
			break;
		}	

		return null;
	}

	public static enum Method {
		GET, POST, PUT, DELETE
	}

}
