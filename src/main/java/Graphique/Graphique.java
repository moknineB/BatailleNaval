package Graphique;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Joueur.classBateau;

public class Graphique extends JFrame implements Graphe
{

  private final int SIZE = 10;
  private final int FIELD_SIZE = 34; 

  private final JButton gameFieldGUI[][] = new JButton[10][10];
  private final JPanel gamePanel = new JPanel();

  private final GraphePlateau grapheplateau;

  private final String IMAGE_PATH = "../resources/images/";

  public Graphique(GraphePlateau grapheplateau)
  { 
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e);
    }
    this.grapheplateau = grapheplateau;
    
    gamePanel.setLayout(new GridBagLayout());
    gamePanel.setPreferredSize(new Dimension(340, 340));
    gamePanel.setBackground(new Color(131, 209, 232));
    gamePanel.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
    
    buildFields();
    
    this.setTitle("BatailleNavale");
    this.setPreferredSize(new Dimension(400, 400));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.add(gamePanel);
    this.pack();
    this.setVisible(true);
  }

  public void displayStrike(int x, int y)
  {
    JButton field = getField(x, y);
    setFieldIcon(field, "strike.png");
  }

  public void displayMissed(int x, int y)
  {
    JButton field = getField(x, y);
    field.setBackground(new Color(85, 185, 218));
  }

  public void displayShip(classBateau ship)
  {
    displayShip(ship.getType(), ship.getX(), ship.getY(), ship.isHorizontal(), ship.getLength());
  }

  public void displayShip(String type, int x, int y, boolean horizontal, int length)
  {
    
    JButton button = new JButton();
    button.setOpaque(false);
    button.setContentAreaFilled(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    button.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
    button.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mousePressed(MouseEvent event)
      {
        System.out.println("Ship sunken");
      }
    });
    
   
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
  
    c.gridx = x;
    c.gridy = y;

    int width = 34;
    int height = 34;
    
    if (horizontal)
    {
      c.gridwidth = length;
      c.gridheight = 1;
      width = 34 * length;
    } else {
      c.gridwidth = 1;
      c.gridheight = length;
      height = 34 * length;
    }
   
    button.setPreferredSize(new Dimension(width, height));
  
    removeFields(x, y, horizontal, length);
    setFieldIcon(button, "ship_" + type + (horizontal ? "h" : "v") + ".png");
    
    
    gamePanel.add(button, c);
    gamePanel.revalidate();
    gamePanel.repaint();
  }

  private boolean shoot(int x, int y)
  {
    
    System.out.printf("At cell %d,%d\n", x, y);
    return grapheplateau.shoot(x, y);
  }

 
  private void buildFields()
  {
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.BOTH;
    
    for (int col = 0; col < 10; col++)
    {
      for (int row = 0; row < 10; row++)
      {
        JButton button = new JButton();
        button.setBackground(new Color(131, 209, 232));
        button.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new java.awt.Dimension(FIELD_SIZE, FIELD_SIZE));
        button.addMouseListener(new MouseAdapter()
        {
          @Override
          public void mousePressed(MouseEvent event)
          {
            JButton button = (JButton) event.getSource();
            Rectangle rectangle = button.getBounds();
            Point point = button.getLocation();
            int row = point.y / rectangle.height;
            int col = point.x / rectangle.width;
            
            shoot(col, row);
          }
        });
        
       
        setField(col, row, button);
        c.gridx = col;
        c.gridy = row;
        gamePanel.add(button, c);
      }
    }
  }

  private void setField(int x, int y, JButton obj)
  {
    gameFieldGUI[y][x] = obj;
  }

  private JButton getField(int x, int y)
  {
    return gameFieldGUI[y][x];
  }

  private void removeFields(int x, int y, boolean horizontal, int length)
  {
    for (int i = 0; i < length; i++)
    {
      if (horizontal)
      {
        gamePanel.remove(getField(x + i, y));
      } else {
        gamePanel.remove(getField(x, y + i));
      }
    }
  }

  private void setFieldIcon(JButton field, String imgPath)
  {
    try {
      Image img = ImageIO.read(getClass().getResource(IMAGE_PATH + imgPath));
      field.setIcon(new ImageIcon(img));
    } catch (IOException e) {
      System.out.println("On ne peut pas voir l'icone: " + e);
    }
  }

 /* public void addHighscore()
  {
    String s = (String) JOptionPane.showInputDialog(frame,"Complete the sentence:\n"+ "\"Green eggs and...\"","Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE, icon,possibilities,"ham");

    //If a string was returned, say so.
    if ((s != null) && (s.length() > 0)) {
      setLabel("Green eggs and... " + s + "!");
      return;
    }
    
    //If you're here, the return value was null/empty.
    setLabel("Finissez votre chemin!");
  }*/

}