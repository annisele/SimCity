package simcity.gui.restauranttwo;
import javax.swing.*;

import simcity.gui.AnimationPanel;
import simcity.gui.Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RestaurantTwoAnimationPanel extends AnimationPanel implements ActionListener {
    private List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());

    public RestaurantTwoAnimationPanel() {
            setBackground(Color.GRAY);
            
            setVisible(true);
        
 
            Timer timer = new Timer(3, this );
            timer.start();
            
    }

        public void actionPerformed(ActionEvent e) {
                repaint();  //Will have paintComponent called
        }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Color tableColor = new Color(200, 200, 200);
            
        //Clear the screen by painting a rectangle the size of the frame
        g2.setColor(getBackground());
        g2.setColor(getBackground());
        g2.fillRect(0, 0, 700, 700);
        g2.setColor(tableColor);
        g2.fillRect(100, 300, 40, 40);
        g2.fillRect(200, 300, 40, 40);
        g2.fillRect(300, 300, 40, 40);
        Color black = new Color(0,0,0);
        g2.setColor(black);
        g2.drawString("Waiting Area", 100, 50);
        //this.g = g;
        Color CookColor = new Color (50, 50, 50);
            g2.setColor(CookColor);
            g2.fillRect(300, 100, 25, 25);
            Color GrillColor = new Color (177, 212, 43);
            g2.setColor(GrillColor);
          
        synchronized(guis) {
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.updatePosition();
            }
        }
        }
         
        synchronized(guis){
        for(Gui gui : guis) {
            if (gui.isPresent()) {
                gui.draw(g2);
            }
        }
        }
    }

   
    
   
}