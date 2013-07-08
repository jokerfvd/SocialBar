package com.socialbar.android.radar;

import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.Establishment;

public class GooglePointer implements Pointer {
	private Establishment establishment;
	private int icon;

	public static Pointer getPointer(Establishment e, int icon) {
		return new GooglePointer(e, icon);
	}

	public static List<Pointer> getPointer(List<Establishment> es, int icon) {
		List<Pointer> pointers = new ArrayList<Pointer>();

		for (Establishment e : es)
			pointers.add(new GooglePointer(e, icon));

		return pointers;
	}

	private GooglePointer(Establishment e, int icon) {
		this.establishment = e;
		this.icon = icon;
	}

	@Override
	public double getLatitude() {
		return this.establishment.getLatitude();
	}

	@Override
	public double getLongitude() {
		return this.establishment.getLongitude();
	}

	@Override
	public String getSnippet() {
		return this.establishment.getID();
	}

	@Override
	public String getTitle() {
		return this.establishment.getName();
	}

	@Override
	public int getIconId() {
		return this.icon;
	}

}
