package simcity.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;


public class AnimationPanel extends JPanel {
	
	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	 
	AnimationPanel() {
		this.setBackground(Color.LIGHT_GRAY);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				changeColor();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}
	
	private void changeColor() {
		setBackground(Color.BLUE);
	}
	
	public void paintComponent(Graphics g) {
		for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw((Graphics2D)g);
            }
        }
	}
	
	/*public void addGui(gui) {
        guis.add(gui);
    }
    
    public void setRestaurantPanel(RestaurantPanel restPanel) {
    	xTables = restPanel.getTableXPos();
        yTables = restPanel.getTableYPos();
    }*/
	
}
