package Graphique;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import Joueur.classBateau;


public class GrapheConsole implements Graphe
{

  private final int SIZE = 10;
  private String[][] gameField;
  private final GraphePlateau plateau;
  private String[] icons;
  private String[] macIcons = { "💧", "💦", "🔥", "⛵", "🚩", "🏊" };
  private String[] winIcons = { "~", "o", "X", "+", "^", "S" };
  private final String coordinateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public GrapheConsole(GraphePlateau plateau)
  {
   
    this.plateau = plateau;
    gameField = new String[SIZE][SIZE];
    icons = winIcons;

    for (String[] row : gameField)
    {
      Arrays.fill(row, icons[0]);
    }
    
    welcome();
    
    displayField();
    inputListener();
  }

  public void displayStrike(int x, int y)
  {
    setField(x, y, icons[2]);
  }

  public void displayMissed(int x, int y)
  {
    setField(x, y, icons[1]);
  }

  public void displayShip(classBateau ship)
  {
    for (int i = 0; i < ship.getLength(); i++)
    {
      if (ship.isHorizontal())
      {
        setField(ship.getX() + i, ship.getY(), icons[4]);
      } else {
        setField(ship.getX(), ship.getY() + i, icons[4]);
      }
    }
    System.out.println("===> Le Bateau est touché!\n");
  }

  private void setField(int x, int y, String icon)
  {
    gameField[y][x] = icon;
  }

  private boolean shoot(int x, char y)
  {
    return shoot(x, coordinateChars.indexOf(Character.toUpperCase(y)));
  }

  private boolean shoot(int x, int y)
  {
    return plateau.shoot(x, y);
  }

  private void displayField()
  {
    String output = "  ";
    for (int i = 0; i < SIZE; i++)
    {
      output += i + ((i < SIZE - 1) ? " " : " --> x\n");
    }
    
    for (int row = 0; row < SIZE; row++)
    {
      output += coordinateChars.charAt(row) + " ";
      for (int col = 0; col < SIZE; col++)
      {
        output += gameField[row][col] + " ";
      }
      output += "\n";
    }
    System.out.println(output);
  }

  public void welcome()
  {
    StringBuilder sb = new StringBuilder();
    
    sb.append("|     Java Bataille Navale    B3 EPSI             /\n");
    sb.append("===> Entrer vos coordonnées: x,y\n");
    
    System.out.println(sb.toString());
  }

  private void inputListener()
  {
    while (true)
    {
      try
      {
    
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        
        if (input.length() < 3)
        {
          System.out.println("quels sont vos coordonnées: y,x");
          continue;
        }
        
        String[] coordinates = input.split(",");
        
        
        char y = coordinates[0].trim().charAt(0);
        int x = Integer.parseInt(coordinates[1].trim());
        
 
        shoot(x, y);
        displayField();
      } catch (IOException e) {
        System.out.println("IOException: " + e);
      }
    }
  }

  private void setIcons()
  {
    if (system().equals("mac"))
    {
      // pour mac
      icons = macIcons;
    } else if (system().equals("win")) {
      // pour windows
      icons = winIcons;
    } else {
      System.out.println("===> le systeme n'est pas supporté :(");
      System.exit(0);
    }
  }

  private String system()
  {
    String os = System.getProperty("os.name").toLowerCase();
    
    if (os.indexOf("mac") >= 0) {
      return "mac";
    } else if (os.indexOf("win") >= 0) {
      return "win";
    } else {
      return "impossible";
    }
  }

}