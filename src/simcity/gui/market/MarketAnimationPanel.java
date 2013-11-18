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

		g2.setColor(Color.CYAN);
		g2.fillRect(50, 50, 50, 100);
		
		super.paintComponent(g);

		//super.guis.add(bob);
	}
	
	public void addGui(Gui g) {
		guis.add(g);
	}

}
