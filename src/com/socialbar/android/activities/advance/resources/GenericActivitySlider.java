package com.socialbar.android.activities.advance.resources;

import android.app.Activity;

import com.socialbar.android.R;
/**
 * Class <code>GenericActivitySlider</code>.
 */
public class GenericActivitySlider extends GenericActivity {
	public GenericActivitySlider(Activity activity) {
		super(activity);
	}

	public void finish() {
		super.finish();
		this.getActivity().overridePendingTransition(R.anim.out_enter,
				R.anim.out_leave);
	}

	public void resume() {
		this.getActivity().overridePendingTransition(R.anim.in_enter,
				R.anim.in_leave);

	}
}
