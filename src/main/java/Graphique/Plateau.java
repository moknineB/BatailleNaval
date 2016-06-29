package Graphique;

public abstract class Plateau
{
  private int length = 1;
  private boolean horizontal = false;
  private int x;
  private int y;

  public Plateau(int x, int y, int length, boolean horizontal)
  {
    if (length > 0)
    {
      this.length = length;
    }
    
    this.horizontal = horizontal;
    this.x = x;
    this.y = y;
  }

  public int getLength()
  {
    return length;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean isHorizontal()
  {
    return horizontal;
  }

  abstract public boolean sunken();

  abstract public void shot();

}