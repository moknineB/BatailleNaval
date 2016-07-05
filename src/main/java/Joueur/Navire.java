package Joueur;

public class Navire extends classBateau {


  public Navire(int x, int y, int length, boolean horizontal)
  {
    super(x, y, length, horizontal);
  }

  public String getType()
  {
    return "navire";
  }
}