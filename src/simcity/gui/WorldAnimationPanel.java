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
import simcity.gui.transportation.CarGui;


public class WorldAnimationPanel extends AnimationPanel implements ActionListener {
	
	private boolean draggable = false;
    private int posX = 0;
    private int posY = 0;
    
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
    
    
    

    
	public WorldAnimationPanel() {//SimCityGui sc) {
		super();

		//intialize the semaphore grid
		for (int i=0; i<gridX+1 ; i++)
		    for (int j = 0; j<gridY+1; j++)
			grid[i][j]=new Semaphore(1,true);
		//build the animation areas
		try {
		    //make the 0-th row and column unavailable
		    for (int i=0; i<gridY+1; i++) grid[0][0+i].acquire();
		    for (int i=1; i<gridX+1; i++) grid[0+i][0].acquire();
		    //restaurant.addWaitArea(2, 2, 13);
		} catch (Exception e) {
		   // System.out.println("Unexpected exception caught in during setup:"+ e);
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
	    	g.setColor(Color.GRAY);
			g.fillRect(720+posX, 143+posY, 50, 20);
			g.fillRect(290+posX, 411+posY, 50, 20);
			g.fillRect(720+posX, 411+posY, 50, 20);
			g.fillRect(1145+posX,411+posY, 50, 20);
			g.fillRect(290+posX, 771+posY, 50, 20);
			g.fillRect(1145+posX,771+posY, 50, 20);
	    	
	    	
	    }
	   
        for(BuildingGui b : buildingGuis) {
        	if (draggable)
        		b.setOffset(posX, posY);
        	b.draw((Graphics2D)g);
        	if ((JPanel)b.getSystem().getAnimationPanel() != super.getSimCityGui().getDetailPane()) {
        		b.getSystem().getAnimationPanel().updateGuis();
        	}
        }
        
        synchronized(busGuis) {
			for(Gui gui : busGuis) {
				if (gui.isPresent()) {
					if (draggable)
						gui.setOffset(posX, posY);
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
		
	     synchronized(carGuis) {
				for(Gui gui : carGuis) {
					if (gui.isPresent()) {
						if (draggable)
							gui.setOffset(posX, posY);
						gui.updatePosition();
					}
				}
			}

			synchronized(carGuis) {
				for(Gui gui : carGuis) {
					if (gui.isPresent()) {
						gui.draw((Graphics2D)g);
					}
				}
			}
		
		synchronized(guis) {
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					if (draggable)
						gui.setOffset(posX, posY);
					gui.updatePosition();
				}
			}
		}

		synchronized(guis) {
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.draw((Graphics2D)g);
				}
			}
		}		
       
	}
	
	public void setBackgroundOne() {
    	background = roadimage;
    	draggable = false;
    }
    
    public void setBackgroundTwo() {
    	background = cityimg;
    	draggable = false;
    }
    
    public void setBackgroundThree() {
    	background = largecityimage;
    	draggable = true;
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
	
	public void addCar(CarGui g) {
		carGuis.add(g);
	}
	public void clear() {
		if (busGuis.size() > 0) {
		busGuis.get(0).clear();
		}
		busGuis.clear();
		super.clear();
	}
	

}