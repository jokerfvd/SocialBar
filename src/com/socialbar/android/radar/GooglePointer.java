package com.socialbar.android.radar;

import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.Establishment;

public class GooglePointer implements Pointer {
	private double latitude;
	private double longitude;
	private int icon;
	private String name;
	private String id;

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
		this.latitude = e.getLatitude();
		this.longitude = e.getLongitude();
		this.name = e.getName();
		this.id = e.getID();
		this.icon = icon;
	}

	@Override
	public double getLatitude() {
		return this.latitude;
	}

	@Override
	public double getLongitude() {
		return this.longitude;
	}

	@Override
	public String getSnippet() {
		return this.id;
	}

	@Override
	public String getTitle() {
		return this.name;
	}

	@Override
	public int getIconId() {
		return this.icon;
	}

}
