package com.socialbar.android.activities.advance.resources;

import android.app.Activity;
import android.os.StrictMode;

import com.socialbar.android.R;
/**
 * Class <code>GenericActivitySlider</code>.
 */
public class GenericActivitySlider extends GenericActivity {
	public GenericActivitySlider(Activity activity) {
		super(activity);
	}
	/**
	 * metodo para setar a transição de encerramento da activity
	 */
	public void finish() {
		super.finish();
		this.getActivity().overridePendingTransition(R.anim.out_enter,
				R.anim.out_leave);
	}
	/**
	 * metodo para setar a transição de inicializacao da activity
	 */
	public void resume() {
		this.getActivity().overridePendingTransition(R.anim.in_enter,
				R.anim.in_leave);

	}
	@Override 
	public void strictMode(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
}
