package Joueur;

import Graphique.Plateau;

abstract public class classBateau extends Plateau {

	private int taille;
	private boolean sunken = false;

	public classBateau(int x, int y, int length, boolean horizontal) {
		super(x, y, length, horizontal);
		taille = this.getLength();
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public boolean sunken() {
		if (taille == 0) {

			sunken = true;
			return true;

		}
		return false;
	}

	public void shot() {
		taille--;
	}

}
