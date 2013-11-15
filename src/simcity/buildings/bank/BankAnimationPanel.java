/*******************
 * Bank Animation Panel - Controls everything within the panel. Creates the roles and GUI's.
 * @author levonne key
 *
 */
package simcity.buildings.bank;

import javax.swing.*;

import restaurant.gui.CustomerGui;
import restaurant.gui.Gui;
import restaurant.gui.WaiterGui;
import simcity.buildings.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class BankAnimationPanel extends JPanel implements ActionListener {

	static final int BANKWINDOWW = 3;
	int BANKWINDOWX[] = {100, 200, 300};
	int BANKWINDOWY[] = {200, 200, 200};
	private final int WINDOWX = 450;
	private final int WINDOWY = 450;
	private Dimension bufferSize;
	private List<Gui> guis = new ArrayList<Gui>();
	
	public AnimationPanel() {
		setSize(WINDOWX, WINDOWY);
        setVisible(true);
        
        bufferSize = this.getSize();
 
    	Timer timer = new Timer(8, this );
    	timer.start();
	}
	public void actionPerformed(ActionEvent e) {		
		repaint();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.fillRect(0, 0, WINDOWX, WINDOWY );

        //Here is the window
        g2.setColor(Color.ORANGE);
       
        for (int i = 0; i < 3; i++){
        	g2.fillRect(BANKWINDOWX[i], BANKWINDOWY[i], BANKWINDOWW, BANKWINDOWW);
        }
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }

        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }  
	}
	public void setpause() {
		   for(Gui gui : guis) {
	           gui.pause(); 
	       }
	   }
	   public void restart() {
		   for(Gui gui : guis) {
	           gui.restart(); 
	       }
	   }
    
}