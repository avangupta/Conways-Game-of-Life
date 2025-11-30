//name:    date:    period: 

//Torbert, e-mail: mr@torbert.com, website: www.mr.torbert.com
//version 6.17.2003

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;

public class LifePanel extends JPanel
{
    //customizable thingies
    private static final int FRAME = 600;
    private static final int CELLS = 10;
    private static final int FPS = 4;
    private static final Color USED_SQUARE_COLOR = new Color(173, 216, 230); //light blue
    private static final Color LIVE_CELL_COLOR = Color.PINK;
    private static final Color DEAD_CELL_COLOR = Color.CYAN;
    private static final Color GRID_COLOR = Color.MAGENTA;
    
    //constants
    private static final int CELLW = FRAME / CELLS;
    private static final int GRID = (int) Math.pow(CELLS, 2);
    private static boolean START = false;
    
    //fields
    private BufferedImage myImage;
    private Graphics myBuffer;
    private Timer t;
    private List<Integer> cell;
    private List<Integer> newCell;
    private List<Integer> cellsVisited;
    
    public LifePanel()
    {
        myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        
        //creates lists and starts cell list with all -1's (unfilled) and no 1's (filled)
        cell = new ArrayList<>();
        for (int i = 1; i <= GRID; i++)
        {
            cell.add(-1);
        }
        newCell = new ArrayList<>();
        cellsVisited = new ArrayList<>();
        
        myBuffer.setColor(DEAD_CELL_COLOR);
        myBuffer.fillRect(0, 0, FRAME, FRAME);
        
        addMouseListener(new Mouse());
        
        System.out.println("Click cells to fill them in and click them again to unfill them.");
        System.out.println("Right click to start the simulation.");
        
        t = new Timer(1000/FPS, new Listener());
        t.start();
    }
    
    public void paintComponent(Graphics g)
    {
        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }
    
    private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            
            //erases screen
            if (START)
            {
            myBuffer.setColor(DEAD_CELL_COLOR);
            myBuffer.fillRect(0, 0, FRAME, FRAME);
            }
            
            //draws grid
            myBuffer.setColor(GRID_COLOR);
            for (int i = CELLW; i < FRAME; i += CELLW)
            {
                myBuffer.drawLine(i, 0, i, FRAME);
                myBuffer.drawLine(0, i, FRAME, i);
            }
            
            if (START)
            {
                for (int i = 0; i <= GRID - 1; i++)
                {
                    //draws cells on grid
                    if (cellsVisited.contains(i))
                    {
                        int xpos = (i % CELLS)*CELLW;
                        int ypos = ((i - (i % CELLS))/CELLS)*CELLW;
                        myBuffer.setColor(USED_SQUARE_COLOR);
                        myBuffer.fillRect(xpos, ypos, CELLW, CELLW);
                    }
                    if (cell.get(i) == 1)
                    {
                        int xpos = (i % CELLS)*CELLW;
                        int ypos = ((i - (i % CELLS))/CELLS)*CELLW;
                        myBuffer.setColor(LIVE_CELL_COLOR);
                        myBuffer.fillRect(xpos, ypos, CELLW, CELLW);
                    }
                }
                newCell.clear();
                newCell.addAll(cell);
                for (int i = 0; i <= GRID - 1; i++)
                {
                    //neighbor counting
                    int neighbors = 0;
                    if (i % CELLS != 0)
                    {
                        if (newCell.get(i-1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (newCell.get(i-1+CELLS) == 1)
                        {
                            neighbors++;
                        }
                    }
                    
                    
                    if (i >= CELLS)
                    {
                        if (newCell.get(i-CELLS) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (newCell.get(i-CELLS+GRID) == 1)
                        {
                            neighbors++;
                        }
                    }
                    
                    
                    if (i % CELLS != 0 && i >= CELLS)
                    {
                        if (newCell.get(i-CELLS-1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (i >= CELLS)
                        {
                            if (newCell.get(i-1) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i % CELLS != 0)
                        {
                            if (newCell.get(i-CELLS-1+GRID) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i == 0)
                        {
                            if (newCell.get(GRID-1) == 1)
                            {
                                neighbors++;
                            }
                        }
                    }
                    
                    
                    if (i % CELLS != CELLS - 1 && i >= CELLS)
                    {
                        if (newCell.get(i-CELLS+1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (i >= CELLS)
                        {
                            if (newCell.get(i-2*CELLS+1) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i % CELLS != CELLS - 1)
                        {
                            if (newCell.get(i-CELLS+1+GRID) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i == CELLS - 1)
                        {
                            if (newCell.get(GRID-CELLS) == 1)
                            {
                                neighbors++;
                            }
                        }
                    }
                    
                    
                    if (i % CELLS != CELLS - 1)
                    {
                        if (newCell.get(i+1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (newCell.get(i+1-CELLS) == 1)
                        {
                            neighbors++;
                        }
                    }
                    
                    
                    if (i <= GRID - CELLS - 1)
                    {
                        if (newCell.get(i+CELLS) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (newCell.get(i-GRID+CELLS) == 1)
                        {
                            neighbors++;
                        }
                    }
                    
                    
                    if (i % CELLS != CELLS - 1 && i <= GRID - CELLS - 1)
                    {
                        if (newCell.get(i+CELLS+1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (i % CELLS != CELLS - 1)
                        {
                            if (newCell.get(i+1-GRID+CELLS) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i <= GRID - CELLS - 1)
                        {
                            if (newCell.get(i+1) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i == GRID - 1)
                        {
                            if (newCell.get(0) == 1)
                            {
                                neighbors++;
                            }
                        }
                    }
                    
                    if (i % CELLS != 0 && i <= GRID - CELLS - 1)
                    {
                        if (newCell.get(i+CELLS-1) == 1)
                        {
                            neighbors++;
                        }
                    }
                    else
                    {
                        if (i % CELLS != 0)
                        {
                            if (newCell.get(i-1-GRID+CELLS) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i <= GRID - CELLS - 1)
                        {
                            if (newCell.get(i + 2 * CELLS - 1) == 1)
                            {
                                neighbors++;
                            }
                        }
                        if (i == GRID - CELLS)
                        {
                            if (newCell.get(CELLS - 1) == 1)
                            {
                                neighbors++;
                            }
                        }
                    }
                    
                    /* System.out.print(neighbors);
                    if (i == GRID - 1)
                    {
                        System.out.println(64);
                    } */
                    
                    //edits cells using four rules
                    if (newCell.get(i) == 1)
                    {
                        if (neighbors < 2 || neighbors > 3)
                        {
                            cell.set(i, -1);
                        }
                        if (!cellsVisited.contains(i))
                        {
                            cellsVisited.add(i);
                        }
                    }
                    else if (newCell.get(i) == -1)
                    {
                        if (neighbors == 3)
                        {
                            cell.set(i, 1);
                        }
                    }
                    
                }
            }
         
            repaint();
        }
    }
    
    private class Mouse extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            //right click checked
            if (e.isMetaDown())
            {
                if (!START)
                {
                    START = true;
                    System.out.println("Simulation started!");
                }
            }
            //uses mouse click x and y and remainder to fill in grid square and fills in array as well
            if (!START)
            {
                int cell_x = (e.getX() - e.getX() % CELLW)/CELLW;
                int cell_y = (e.getY() - e.getY() % CELLW)/CELLW;
                int index = cell_x + cell_y * CELLS;
            
                cell.set(index, -1 * cell.get(index));
            
                if (cell.get(index) == -1)
                {
                    myBuffer.setColor(DEAD_CELL_COLOR);
                }
                else if (cell.get(index) == 1)
                {
                    myBuffer.setColor(LIVE_CELL_COLOR);
                }
                myBuffer.fillRect(cell_x * CELLW, cell_y * CELLW, CELLW, CELLW);
            }
        }
    }
}
