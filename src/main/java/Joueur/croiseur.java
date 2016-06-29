package Joueur;

public class croiseur extends classBateau {

	  public croiseur(int x, int y, int length, boolean horizontal)
	  {
	    super(x, y, length, horizontal);
	  }

	  public String getType()
	  {
	    return "croiseur";
	  }

}
