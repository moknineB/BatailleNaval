
package Graphique;

import Joueur.classBateau;

public interface Graphe {

	public void displayStrike(int x, int y);

	public void displayMissed(int x, int y);

	public void displayShip(classBateau navire);
	
}