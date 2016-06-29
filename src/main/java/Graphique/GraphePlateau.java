
import java.util.Arrays;
import java.util.Random;

import Joueur.classBateau;
import Joueur.croiseur;
import Joueur.sousmarin;

public class GraphePlateau
{
  private final int size;
  private int sunkenShips = 0;
  private int strikeCount = 0;
  private int shotCount = 0;
  private int shipCount = 0;

  private Plateau[][] gameField;
  private final int[] shipTypes = { 1, 2, 3 };

  private final Graphe gui = new GrapheConsole(this); 
  

  public GraphePlateau(int size)
  {
        System.out.println("########### hello world");
    this.size = size;
    
    gameField = new Plateau[size][size];
    
    for (Plateau[] row : gameField)
    {
      Arrays.fill(row, new Plateau1());
    }

    generateShips();
    debuging();
  }

  public boolean solved()
  {
    if (sunkenShips == shipCount)
    {
      System.out.printf("===> Super! un bateau a été destruis!\n", shipCount, shotCount);

      return true;
    }
    return false;
  }
  
  public boolean shoot(int x, int y)
  {
    System.out.println("SIZE: " + this.size);
 
    Plateau field = getPlateau(x, y);
    
 
    shotCount++;

    if (x >= size || y >= size)
    {
      System.out.println("===> Coordonnées à l'exterieur du plateau!");
      return false;
    }
    

    if (field instanceof classBateau)
    {

    	classBateau ship = (classBateau) field;
      

      if (ship.sunken())
      {
        return false;
      }
      
      
      ship.shot();
      
      gui.displayStrike(x, y);
      strikeCount++;
      
      if (ship.sunken())
      {
        gui.displayShip(ship);
        sunkenShips++;
      }
      
      return true;
    } else {
    	
    	Plateau1 singleField = (Plateau1) field;
      singleField.shot();
    }
    
    
    gui.displayMissed(x, y);
    return false;
  }


  private void generateShips()
  {    
    shipCount = 10;
    
    for (int i = 0; i < shipCount; i++)
    {
      createShip();
    }
  }
  
  private void createShip()
  {
    Random random = new Random();
    int x, y;
    boolean horizontal;
    int maxLength = 4;
    int length = random.nextInt(maxLength - 2 + 1) + 2;
    
    boolean shipCreated;
    
    do {
      x = random.nextInt(size);
      y = random.nextInt(size);
      horizontal = random.nextBoolean();
      shipCreated = setShip(x, y, length, horizontal);
    } while (!shipCreated);
  }

  private boolean setShip(int x, int y, int length, boolean horizontal)
  {
    if (!isOccupied(x, y) && checkFieldLength(x, y, length, horizontal) && checkFieldArea(x, y, length, horizontal))
    {
    	classBateau ship;
      
    
      switch (length)
      {
        case 2:
         
          ship = new Boat(x, y, length, horizontal);
          break;
        case 3:
          if (x < 4) 
          {
           
            ship = new Destroyer(x, y, length, horizontal);
          } else {
           
            ship = new sousmarin(x, y, length, horizontal);
          }
          break;
        case 4:
         
          ship = new croiseur(x, y, length, horizontal);
          break;
        case 5:
          
          ship = new AirCarrier(x, y, length, horizontal);
          break;
        default:
          
          ship = new Boat(x, y, length, horizontal);
          break;
      }
      
   
      for (int i = 0; i < length; i++)
      {
        if (horizontal)
        {
          setField(x + i, y, ship);
        } else {
          setField(x, y + i, ship);
        }
      }
      return true;
    }
    return false;
  }


  private boolean checkFieldLength(int x, int y, int length, boolean horizontal)
  {

    if (x < size && y < size)
    {

      if (horizontal)
      {
        if ((x + length - 1) < size)
        {
          return true;
        }
      } else {
        if ((y + length - 1) < size)
        {
          return true;
        }
      }
    }
    return false;
  }


  private boolean checkFieldArea(int x, int y, int length, boolean horizontal)
  {
    
    if (horizontal)
    {
   
      if (!(getPlateau(x + length, y) instanceof Plateau1) || !(getPlateau(x - 1, y) instanceof Plateau1))
      {
        return false;
      }
    } else {
  
      if (!(getPlateau(x, y + length) instanceof Plateau1) || !(getPlateau(x, y - 1) instanceof Plateau1))
      {
        return false;
      }
    }
    
 
    for (int i = 0; i < length; i++)
    {
      if (horizontal)
      {
       
        if (!(getPlateau(x + i, y - 1) instanceof Plateau1) || !(getPlateau(x + i, y + 1) instanceof Plateau1))
        {
          return false;
        }
      } else {
   
        if (!(getPlateau(x - 1, y + i) instanceof Plateau1) || !(getPlateau(x + 1, y + i) instanceof Plateau1))
        {
          return false;
        }
      }
    }

   
    return true;
  }

  private boolean isOccupied(int x, int y)
  {
    return !(getPlateau(x, y) instanceof Plateau1);
  }

  
  private Plateau getPlateau(int x, int y)
  {
    if (x >= 0 && y >= 0 && x < size && y < size)
    {
      return gameField[y][x];
    }
  
    return new Plateau1();
  }

 
  private void setField(int x, int y, Plateau elem)
  {
    if (x >= 0 && y >= 0 && x < size && y < size)
    {
      gameField[y][x] = elem;
    }
  }
  
  public String statistic()
  {
    String output = "";
    output += String.format("+======= Game statistic =======+\n");
    output += String.format("| ⛵  Destroyed:     %-10s |\n", sunkenShips);
    output += String.format("| 🎯  Strikes:       %-10s |\n", strikeCount);
    output += String.format("| 🔫  Shots:         %-10s |\n", shotCount);
    output += String.format("+==============================+\n");
    return output;
  }

  private int calculatePoints()
  {
    if ((shotCount - strikeCount + 1) > 0)
    {
      return (strikeCount * 4) / (shotCount - strikeCount + 1);
    }
    return 0;
  }

  private void debuging()
  {
    String output = "";
    for (int row = 0; row < size; row++)
    {
      for (int col = 0; col < size; col++)
      {
        if (!(getPlateau(col, row) instanceof Plateau1))
        {
          output += (getPlateau(col, row) instanceof classBateau) ? "X" : "S";
        } else {
          output += "#";
        }
        output += " ";
      }
      output += "\n";
    }
    System.out.println(output);
  }
}