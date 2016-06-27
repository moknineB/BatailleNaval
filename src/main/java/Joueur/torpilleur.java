package Joueur;

abstract public class torpilleur extends classBateau {

	  public torpilleur(int x, int y, int length, boolean horizontal)
	  {
	    super(x, y, length, horizontal);
	  }

	  public String getType()
	  {
	    return "torpilleur";
	  }

}
