
import java.util.Arrays;
import java.util.Random;

public class GraphePlateau
{
  private final int size;
  private int sunkenShips = 0;
  private int strikeCount = 0;
  private int shotCount = 0;
  private int shipCount = 0;

  private Field[][] gameField;
  private final int[] shipTypes = { 1, 2, 3 };

  // unit feature classes
  private final Graphe gui = new Graphe(this); 
  

  public GraphePlateau(int size)
  {
        System.out.println("########### hello world");
    // prepeare field
    this.size = size;
    
    gameField = new Field[size][size];
    
    for (Field[] row : gameField)
    {
      Arrays.fill(row, new SingleField());
    }

    generateShips();
    debuging();
  }


  public boolean solved()
  {
    if (sunkenShips == shipCount)
    {
      System.out.printf("===> Super! un bateau a été destroy!\n", shipCount, shotCount);

      return true;
    }
    return false;
  }
  
  public boolean shoot(int x, int y)
  {
    System.out.println("SIZE: " + this.size);
 
    Field field = getField(x, y);
    
 
    shotCount++;

    if (x >= size || y >= size)
    {
      System.out.println("===> Coordinates out of range!");
      return false;
    }
    

    if (field instanceof Ship)
    {

      Ship ship = (Ship) field;
      

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
    	
      SingleField singleField = (SingleField) field;
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
    
    // 1x4 // 2x3 //3x2 // 4x1
    
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
      Ship ship;
      
    
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
           
            ship = new Submarine(x, y, length, horizontal);
          }
          break;
        case 4:
         
          ship = new Cruiser(x, y, length, horizontal);
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
   
      if (!(getField(x + length, y) instanceof SingleField) || !(getField(x - 1, y) instanceof SingleField))
      {
        return false;
      }
    } else {
  
      if (!(getField(x, y + length) instanceof SingleField) || !(getField(x, y - 1) instanceof SingleField))
      {
        return false;
      }
    }
    
 
    for (int i = 0; i < length; i++)
    {
      if (horizontal)
      {
       
        if (!(getField(x + i, y - 1) instanceof SingleField) || !(getField(x + i, y + 1) instanceof SingleField))
        {
          return false;
        }
      } else {
   
        if (!(getField(x - 1, y + i) instanceof SingleField) || !(getField(x + 1, y + i) instanceof SingleField))
        {
          return false;
        }
      }
    }

   
    return true;
  }

  private boolean isOccupied(int x, int y)
  {
    return !(getField(x, y) instanceof SingleField);
  }

  
  private Field getField(int x, int y)
  {
    if (x >= 0 && y >= 0 && x < size && y < size)
    {
      return gameField[y][x];
    }
  
    return new SingleField();
  }

 
  private void setField(int x, int y, Field elem)
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
        if (!(getField(col, row) instanceof SingleField))
        {
          output += (getField(col, row) instanceof Ship) ? "X" : "S";
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