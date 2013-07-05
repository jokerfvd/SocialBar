package com.socialbar.android.rest.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONStringer;

import com.socialbar.android.rest.resource.Estabelecimento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;


/**
 * Service API
 *
 * @author Duval
 */
public class ServiceHelper {

	
	public static String ACTION_REQUEST_RESULT = "REQUEST_RESULT";
	public static String EXTRA_REQUEST_ID = "EXTRA_REQUEST_ID";
	public static String EXTRA_RESULT_CODE = "EXTRA_RESULT_CODE";
	
	private static final String REQUEST_ID = "REQUEST_ID";

	private static final String estabelecimentoHashkey = "UMESTAB";
	private static final String estabelecimentosHashkey = "ESTABS";
	private static final String estabelecimentosPorCoordenadaHashkey = "ESTABSCOORDENADA";
	private static final String estabelecimentosPorFiltrokey = "ESTABSFILTRO";
	private static final String insereEstabelecimentoHashkey = "INSEREESTAB";
	private static final String removeEstabelecimentoHashkey = "REMOVEESTAB";
	private static final String editEstabelecimentoHashkey = "EDITESTAB";
	
	private static Object lock = new Object();

	private static ServiceHelper instance;

	//TODO: refactor the key
	private Map<String,Long> pendingRequests = new HashMap<String,Long>();
	private Context ctx;

	private ServiceHelper(Context ctx){
		this.ctx = ctx.getApplicationContext();
	}

	public static ServiceHelper getInstance(Context ctx){
		synchronized (lock) {
			if(instance == null){
				instance = new ServiceHelper(ctx);			
			}
		}

		return instance;		
	}
	
	public long removeEstabelecimento(String id) {
		if(pendingRequests.containsKey(removeEstabelecimentoHashkey)){
			return pendingRequests.get(removeEstabelecimentoHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(removeEstabelecimentoHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleRemoveEstabelecimentoResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_DELETE);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTO);
		intent.putExtra(Service.RESOURCE_ID, id);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		this.ctx.startService(intent);
		
		return requestId;
	}
	
	private void handleRemoveEstabelecimentoResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(removeEstabelecimentoHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
	
	public long editEstabelecimento(String json, String id) {
		if(pendingRequests.containsKey(editEstabelecimentoHashkey)){
			return pendingRequests.get(editEstabelecimentoHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(editEstabelecimentoHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleEditEstabelecimentoResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_PUT);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTO);
		intent.putExtra(Service.RESOURCE_JSON, json);
		intent.putExtra(Service.RESOURCE_ID, id);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		this.ctx.startService(intent);
		
		return requestId;
	}
	
	private void handleEditEstabelecimentoResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(editEstabelecimentoHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
	
	public long addEstabelecimento(String json) {
		if(pendingRequests.containsKey(insereEstabelecimentoHashkey)){
			return pendingRequests.get(insereEstabelecimentoHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(insereEstabelecimentoHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleAddEstabelecimentoResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_POST);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTO);
		intent.putExtra(Service.RESOURCE_JSON, json);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		this.ctx.startService(intent);
		
		return requestId;
	}
	
	private void handleAddEstabelecimentoResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(insereEstabelecimentoHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
	
	public long getEstabelecimento(String id) {
		if(pendingRequests.containsKey(estabelecimentoHashkey)){
			return pendingRequests.get(estabelecimentoHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(estabelecimentoHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetEstabelecimentoResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_GET);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTO);
		intent.putExtra(Service.RESOURCE_ID, id);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		//precisa declarar no manifesto
		this.ctx.startService(intent);
		
		return requestId;		
	}

	public long getEstabelecimentos() {
		if(pendingRequests.containsKey(estabelecimentosHashkey)){
			return pendingRequests.get(estabelecimentosHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(estabelecimentosHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetEstabelecimentosResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_GET);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTOS);
		intent.putExtra(Service.ESTABS_TIPO, Service.ESTABS_NORMAL);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		//precisa declarar no manifesto
		this.ctx.startService(intent);
		
		return requestId;		
	}
	
	public long getEstabelecimentos(double latitude, double longitude) {
		if(pendingRequests.containsKey(estabelecimentosPorCoordenadaHashkey)){
			return pendingRequests.get(estabelecimentosPorCoordenadaHashkey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(estabelecimentosPorCoordenadaHashkey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetEstabelecimentosPorCoordenadaResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_GET);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTOS);
		intent.putExtra(Service.ESTABS_TIPO, Service.ESTABS_COORDENADA);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		//precisa declarar no manifesto
		this.ctx.startService(intent);
		
		return requestId;	
	}
	
	public long getEstabelecimentos(String... args) {
		if(pendingRequests.containsKey(estabelecimentosPorFiltrokey)){
			return pendingRequests.get(estabelecimentosPorFiltrokey);
		}

		long requestId = generateRequestID();
		pendingRequests.put(estabelecimentosPorFiltrokey, requestId);

		ResultReceiver serviceCallback = new ResultReceiver(null){
			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {
				handleGetEstabelecimentosPorFiltroResponse(resultCode, resultData);
			}
		};

		Intent intent = new Intent(this.ctx, Service.class);
		intent.putExtra(Service.METHOD_EXTRA, Service.METHOD_GET);
		intent.putExtra(Service.RESOURCE_TYPE_EXTRA, Service.RESOURCE_TYPE_ESTABELECIMENTOS);
		intent.putExtra(Service.ESTABS_TIPO, Service.ESTABS_FILTRO);
		intent.putExtra(Service.SERVICE_CALLBACK, serviceCallback);
		intent.putExtra(REQUEST_ID, requestId);

		//precisa declarar no manifesto
		this.ctx.startService(intent);
		
		return requestId;	
	}

	private long generateRequestID() {
		long requestId = UUID.randomUUID().getLeastSignificantBits();
		return requestId;
	}

	public boolean isRequestPending(long requestId){
		return this.pendingRequests.containsValue(requestId);
	}

	private void handleGetEstabelecimentoResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(estabelecimentoHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}

	private void handleGetEstabelecimentosResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(estabelecimentosHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
	
	
	private void handleGetEstabelecimentosPorCoordenadaResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(estabelecimentosPorCoordenadaHashkey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
	
	private void handleGetEstabelecimentosPorFiltroResponse(int resultCode, Bundle resultData){

		Intent origIntent = (Intent)resultData.getParcelable(Service.ORIGINAL_INTENT_EXTRA);

		if(origIntent != null){
			long requestId = origIntent.getLongExtra(REQUEST_ID, 0);

			pendingRequests.remove(estabelecimentosPorFiltrokey);

			Intent resultBroadcast = new Intent(ACTION_REQUEST_RESULT);
			resultBroadcast.putExtra(EXTRA_REQUEST_ID, requestId);
			resultBroadcast.putExtra(EXTRA_RESULT_CODE, resultCode);

			ctx.sendBroadcast(resultBroadcast);
		}
	}
}
