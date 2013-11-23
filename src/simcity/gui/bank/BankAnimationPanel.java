package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import javax.swing.*;

/****************
 * Bank Animation Panel - 
 * @author levonne key
 */
public class BankAnimationPanel extends AnimationPanel implements ActionListener {
	private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	ImageIcon ii = new ImageIcon("res/Bank/bankgui.png");
    Image img = ii.getImage();
    Image homeimage = img.getScaledInstance(470, 454,  java.awt.Image.SCALE_SMOOTH); 
	public BankAnimationPanel() {
		super();
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		//Clear the screen by painting a rectangle the size of the frame
				g2.setColor(Color.LIGHT_GRAY);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				g2.drawImage(homeimage, 0,0, null);
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