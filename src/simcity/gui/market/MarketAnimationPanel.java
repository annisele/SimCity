package simcity.gui.market;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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
	
	private List<Gui> guis = new ArrayList<Gui>();

	public MarketAnimationPanel() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		//walls
		g2.setColor(Color.GRAY);
		g2.fillRect(100 , 0, this.getWidth(), WALL_WIDTH); //top wall
		g2.fillRect(0 , 40, WALL_WIDTH, this.getHeight()); //left wall
		g2.fillRect(this.getWidth() - WALL_WIDTH , 0, WALL_WIDTH, this.getHeight()); //right wall
		g2.fillRect(0 , this.getHeight() - WALL_WIDTH, this.getWidth(), WALL_WIDTH); //bottom wall

		//cash register
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(250, 50, 75, 30);
		
		//shelves
		g2.setColor(new Color(100, 65, 45));
		for(int i = 0; i < NUM_SHELVES; i++) {
			g2.fillRect((i * 70) + 40, 150, 30, 250);
		}

		super.paintComponent(g);

		//super.guis.add(bob);
	}

	public void addGui(Gui g) {
		guis.add(g);
	}

}
