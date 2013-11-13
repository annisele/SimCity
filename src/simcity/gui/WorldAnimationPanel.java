package simcity.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class WorldAnimationPanel extends AnimationPanel implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
        //Clear the screen by painting a rectangle the size of the frame
		
        g2.setColor(Color.BLACK);
        g2.fillRect(100, 100,200, 200);
        
		super.paintComponent(g);
	}
	

	
	
}