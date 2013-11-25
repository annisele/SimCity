package simcity.gui;

import javax.swing.*;

import simcity.gui.transportation.BusGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WorldAnimationPanel extends AnimationPanel implements ActionListener {
	
	protected List<BusGui> busGuis = Collections.synchronizedList(new ArrayList<BusGui>());
	
	ImageIcon ii = new ImageIcon("res/citygui/basicroad.png");
    Image img = ii.getImage();
    Image roadimage = img.getScaledInstance(388, 400,  java.awt.Image.SCALE_SMOOTH); 

	public WorldAnimationPanel() {//SimCityGui sc) {
		super();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(getBackground());
		//super.paintComponent(g);
		
        //Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
	    g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	 //   g2.drawImage(roadimage, 40,32, null);
	    
        //here is the building gui
	    g2.setColor(Color.BLUE);
	    
        for(BuildingGui b : buildingGuis) {
        	b.draw((Graphics2D)g);
        	if ((JPanel)b.getSystem().getAnimationPanel() != super.getSimCityGui().getDetailPane()) {
        		//System.out.println("hey "+ b.getSystem()+"   "+ b.getSystem().getAnimationPanel());
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
        
        /*
        for (BusGui b : busGuis) {
        	//System.out.println("I should have one busGui");
        	if (b.isPresent()) {
				b.updatePosition();
			}
        	b.draw((Graphics2D)g);
        }*/
        
        super.paintComponent(g);
		
       
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
		busGuis.clear();
		super.clear();
	}
	

}