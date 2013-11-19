package simcity.gui.bank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

	public BankAnimationPanel() {
		setBackground(Color.YELLOW);
		setVisible(true);	 
        //Timer timer = new Timer(3, this );
        //timer.start();
	}
	public void actionPerformed(ActionEvent e) {
		repaint();
	}	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Color windowColor = new Color (250, 250, 250);
		//Clear the screen by painting a rectangle the size of the frame
		/*g2.setColor(getBackground());
        g2.setColor(getBackground());
        g2.fillRect(0, 0, 600, 600);
        g2.setColor(windowColor);
        g2.fillRect(100, 350, 40, 40);
        g2.fillRect(200, 350, 40, 40);
        g2.fillRect(300, 350, 40, 40);
        //bankteller gui
        Color black = new Color(0,0,0);
        g2.setColor(black);
        g2.drawString("Bank Waiting Area", 100, 50);
		Color BanktellerColor = new Color (45, 45, 45);
			g2.setColor(BanktellerColor);
			g2.fillRect(300, 100, 25, 25);
		Color BankHostColor = new Color (177, 212, 43);
			g2.setColor(BankHostColor);
			g2.fillRect(200, 100, 50, 50);
		*/
		g2.setColor(Color.YELLOW);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		super.paintComponent(g);
		
		
	}
}