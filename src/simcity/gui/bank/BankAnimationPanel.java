package simcity.gui.bank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import simcity.gui.AnimationPanel;
import simcity.gui.Gui;
import simcity.gui.SimCityGui;

/****************
 * Bank Animation Panel - 
 * @author levonne key
 */
public class BankAnimationPanel extends AnimationPanel implements ActionListener {
	private List<Gui> guis = new ArrayList<Gui>();

    private final int WINDOWX = 500;
    private final int WINDOWY = 450;
    private Dimension bufferSize;
	public BankAnimationPanel() {
		super();
		setSize (WINDOWX, WINDOWY);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		//Clear the screen by painting a rectangle the size of the frame
		g2.setColor(Color.ORANGE);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

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
		super.paintComponent(g);
	}
	
	public void addGui(Gui g) {
		guis.add(g);
	}
}