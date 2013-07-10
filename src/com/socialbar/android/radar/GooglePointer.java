package com.socialbar.android.radar;

import java.util.ArrayList;
import java.util.List;

import com.socialbar.android.model.Establishment;

/**
 * Class <code>GooglePointer</code>.
 */
public class GooglePointer implements Pointer {
	private Establishment establishment;
	private int icon;
	/**
	 * metodo estatico para gerar googlepointer de estabelecimento
	 * @param e
	 * @param icon
	 * @return
	 */
	public static Pointer getPointer(Establishment e, int icon) {
		return new GooglePointer(e, icon);
	}
	/**
	 * metodo estatico para gerar googlepointers de uma lista de estabelecimentos
	 * @param e
	 * @param icon
	 * @return
	 */
	public static List<Pointer> getPointer(List<Establishment> es, int icon) {
		List<Pointer> pointers = new ArrayList<Pointer>();

		for (Establishment e : es)
			pointers.add(new GooglePointer(e, icon));

		return pointers;
	}
	/**
	 * construtor
	 * @param e estabelecimento
	 * @param icon icone do mapa
	 */
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
