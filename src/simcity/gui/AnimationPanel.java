package simcity.gui;

import java.awt.*;

import simcity.Clock;
import simcity.gui.transportation.BusGui;

import java.awt.event.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class AnimationPanel extends JPanel implements ActionListener{

	SimCityGui simCityGui;
	JPanel controlPanel;

	protected List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	protected List<BuildingGui> buildingGuis = Collections.synchronizedList(new ArrayList<BuildingGui>());
	protected List<BusGui> busGuis = Collections.synchronizedList(new ArrayList<BusGui>());
	// 
	// have a timer that calls repaint() on every panel
	//private Timer repaintTimer = new Timer();
	//
	// keep track of: how fast was I going, how much time has passed since the last update
	//
	// If we have a base System class, then we can link up the ControlPanel to the animationPanel.  The system
	// class would call AnimationPanel.setControlPanel() and give the animationpanel a controlpanel.
	// Then the animationPanel would call controlPanel.update() passing a building or person
	//

	private int px; //x where mouse was pressed
	private int py;

	protected AnimationPanel(){//SimCityGui sc) {

		//simCityGui = sc;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				px = e.getXOnScreen();
				py = e.getYOnScreen();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent me) {
				// super.mouseClicked(me);
				for (Gui g : guis) {
					if (g.contains(me.getPoint()) && g.isPresent()) {//check if mouse is clicked within shape

						//we can either just print out the object class name
						System.out.println("Clicked a "+g.getClass().getName());

						//controlPanel.updateSelected(g.getPerson());

					}
				}
				// for (BuildingGui g : buildingGuis) {
				for(BuildingGui g : buildingGuis) {
					if (g.contains(me.getPoint())) {//check if mouse is clicked within shape

						//if (controlPanel == null)
						//System.out.println("The buildingGui's animationPanel is null!");
						if (simCityGui != null) {
							simCityGui.changeDetailPane(g.getSystem().getAnimationPanel());

							if (g.getSystem().getControlPanel() == null) {
								System.out.println("We don't have a controlPanel for this building!");


							} else {
								simCityGui.setControlPanel(g.getSystem().getControlPanel());
								//g.getControlPanel().updateSelected(g);
								//System.out.println(Clock.getTime());
							}
						}



					}

				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				//added this to try to set up dragging world map
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});

		Timer timer = new Timer(10, this );
		timer.start();
	}

	public void paintComponent(Graphics g) {
		//System.out.println(" There are "+buildingGuis.size());
		// compute dt, then send dt to every gui for updatePosition
		//System.out.println("I have " + guis.size()+" guis");
		synchronized(guis) {
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}
		}

		synchronized(guis) {
			for(Gui gui : guis) {
				if (gui.isPresent()) {
					gui.draw((Graphics2D)g);
				}
			}
		}
		
		/*synchronized(busGuis) {
			for (BusGui bg : busGuis) {
				if (bg.isPresent()) {
					bg.updatePosition();
				}
			}
		} */
		
	/*	synchronized(busGuis) {
			for(BusGui bg : busGuis) {
				if (bg.isPresent()) {
					bg.draw((Graphics2D)g);
				}
			}
		} */

		//repaint();
	}
	
	public void updateGuis() {
		synchronized(guis) {
			for (Gui gui : guis) {

				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}
		}
		/*synchronized(busGuis) {
			for (BusGui bg : busGuis) {
				
				if (bg.isPresent()) {
					//bg.updatePosition();
				}
			}
		} */
	}

	public void addGui(Gui gui) {
		guis.add(gui);
	}

	public void removeGui(Gui gui) {
		guis.remove(gui);
	}

	public void addBuilding(BuildingGui b) {
		buildingGuis.add(b);
	}
	
	public void addBus(BusGui g) {
		busGuis.add(g);
	}

	public void setControlPanel(JPanel cp) {
		controlPanel = cp;
	}

	public JPanel getControlPanel() {
		return controlPanel;
	}

	public void setSimCityGui(SimCityGui scg) {
		simCityGui = scg;

	}
	
	public SimCityGui getSimCityGui() {
		return simCityGui;
	}

	public void clear() {
		guis.clear();
		buildingGuis.clear();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}



	/*public void addGui(gui) {
        guis.add(gui);
    }

    public void setRestaurantPanel(RestaurantPanel restPanel) {
    	xTables = restPanel.getTableXPos();
        yTables = restPanel.getTableYPos();
    }*/

}
