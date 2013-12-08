package simcity.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import astar.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import simcity.gui.transportation.BusGui;


public class WorldAnimationPanel extends AnimationPanel implements ActionListener {
	static int gridX = 36; // newly added
	static int gridY = 36; // newly added
	Semaphore[][] grid = new Semaphore[gridX+1][gridY+1]; 
	protected List<BusGui> busGuis = Collections.synchronizedList(new ArrayList<BusGui>());
	    
    ImageIcon ii = new ImageIcon("res/citygui/basicroad.png");
    Image img = ii.getImage();
    Image roadimage = img.getScaledInstance(388, 400,  java.awt.Image.SCALE_SMOOTH); 
    ImageIcon ii2 = new ImageIcon("res/citygui/simcitymap2.png");
    Image cityimg = ii2.getImage();
    ImageIcon ii3 = new ImageIcon("res/citygui/simcitymap3.png");
    Image largecityimage = ii3.getImage();
    //Image largecityimage = intersection.getScaledInstance(462,  453,  java.awt.Image.SCALE_SMOOTH);
    
    Image background = null;
    
    private int posX = 0;
    private int posY = 0;
    

    
	public WorldAnimationPanel() {//SimCityGui sc) {
		super();

		//intialize the semaphore grid
		for (int i=0; i<gridX+1 ; i++)
		    for (int j = 0; j<gridY+1; j++)
			grid[i][j]=new Semaphore(1,true);
		//build the animation areas
		try {
		    //make the 0-th row and column unavailable
		    System.out.println("making row 0 and col 0 unavailable.");
		    for (int i=0; i<gridY+1; i++) grid[0][0+i].acquire();
		    for (int i=1; i<gridX+1; i++) grid[0+i][0].acquire();
		    System.out.println("adding wait area");
		    //restaurant.addWaitArea(2, 2, 13);
		} catch (Exception e) {
		    System.out.println("Unexpected exception caught in during setup:"+ e);
		}


	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	@Override
	public void dragWorld(int x, int y) {
		if (	(posX >= -(largecityimage.getWidth(simCityGui)-this.getWidth())+10 || x < 0) 
			&&	(posX <= -10 || x > 0)	) {
			posX -= x;
		}
		//if (posY <= largecityimage.getHeight(simCityGui))
		if (	(posY >= -(largecityimage.getHeight(simCityGui)-this.getHeight())+10 || y < 0) 
			&&	(posY <= -10 || y > 0)	) {
			posY -= y;
		}
		
		System.out.println(x+", "+y);
		return;
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(getBackground());
		//super.paintComponent(g);
		
        //Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
	    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	    
	    if (background == roadimage) {
	    	g2.drawImage(background, 40, 32, null);

	    } else if (background == cityimg) {
	    	g2.drawImage(background, 0, 0, null);
	    }
	    else if (background == largecityimage) {
	    	g2.drawImage(background, posX, posY, null);
	    }
    
        for(BuildingGui b : buildingGuis) {
        	b.draw((Graphics2D)g);
        	if ((JPanel)b.getSystem().getAnimationPanel() != super.getSimCityGui().getDetailPane()) {
        		b.getSystem().getAnimationPanel().updateGuis();
        	}
        }
        
        synchronized(busGuis) {
			for(Gui gui : busGuis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}
		}

		synchronized(busGuis) {
			for(Gui gui : busGuis) {
				if (gui.isPresent()) {
					gui.draw((Graphics2D)g);
				}
			}
		}

        super.paintComponent(g);
		
       
	}
	
	public void setBackgroundOne() {
    	background = roadimage;
    }
    
    public void setBackgroundTwo() {
    	background = cityimg;
    }
    
    public void setBackgroundThree() {
    	background = largecityimage;
    }
    
	public void addGui(Gui g) {
		guis.add(g);
	}
	
	public void addBuilding(BuildingGui g) {
		buildingGuis.add(g);
	}
	
	public void addBus(BusGui g) {
		busGuis.add(g);
		
	}
	
	public void clear() {
		if (busGuis.size() > 0) {
		busGuis.get(0).clear();
		}
		busGuis.clear();
		super.clear();
	}
	

}