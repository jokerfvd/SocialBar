package com.socialbar.android.model;

/**
 * Interface <code>Model</code>. Interface eventos do modelo.
 */
public interface ModelEvent {
	/**
	 * evento a ser chamado dentro do modelo
	 * @param c
	 * @param data
	 */
	public void onModelReceive(Class c, Object data);
	
}
