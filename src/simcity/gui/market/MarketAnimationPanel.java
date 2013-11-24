package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.ImageIcon;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;


/****************
 * Market Animation Panel - Controls market GUI.
 * @author rebeccahao
 *
 */
public class MarketAnimationPanel extends AnimationPanel implements ActionListener  {

	private final int NUM_SHELVES = 6;
	private final int WALL_WIDTH = 5;
	
	 ImageIcon ii = new ImageIcon("res/Market/marketinterior.png");
	    Image img = ii.getImage();
	    Image marketimage = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH); 
	
	public MarketAnimationPanel() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2.drawImage(marketimage, 0,0, null); 


		super.paintComponent(g);

		//super.guis.add(bob);
	}

	public void addGui(Gui g) {
		guis.add(g);
	}

}
