package Partie;

import java.util.ArrayList;
import Graphique.GraphePlateau;
import Joueur.classBateau;
import Joueur.classJoueur;

public class classPartie 
{
	public static void main(String[] args)
	  {
	    classPartie game = new classPartie(10);
	  }

	  public classPartie(int size)
	  {
	    GraphePlateau plateau = new GraphePlateau(size);
	  }
}
