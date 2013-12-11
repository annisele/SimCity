package simcity.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import simcity.gui.market.MarketCashierGui;
import simcity.gui.market.MarketCustomerGui;
import simcity.gui.market.MarketWorkerGui;
import simcity.gui.trace.AlertLog;
import simcity.gui.trace.AlertTag;
import simcity.gui.transportation.BusGui;
import simcity.gui.transportation.CarGui;

public class AnimationPanel extends JPanel implements ActionListener{

	SimCityGui simCityGui;
	JPanel controlPanel;
	PersonPanel pPanel= new PersonPanel();

	protected List<Gui> guis = Collections.synchronizedList(new ArrayList<Gui>());
	protected List<BuildingGui> buildingGuis = Collections.synchronizedList(new ArrayList<BuildingGui>());
	protected List<BusGui> busGuis = Collections.synchronizedList(new ArrayList<BusGui>());
	protected List<CarGui> carGuis = Collections.synchronizedList(new ArrayList<CarGui>());


	private int px; //x where mouse was pressed
	private int py;
	
	protected int xStart = 0;
	protected int yStart = 0;
	protected int xOffset = 0;
    protected int yOffset = 0;

	protected AnimationPanel(){

		addMouseListener(new MouseListener() {
			

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				px = e.getXOnScreen();
				py = e.getYOnScreen();
				xStart = (int) e.getPoint().getX();
				yStart = (int) e.getPoint().getY();
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent me) {
				for (Gui g : guis) {
					if (g.contains(me.getPoint()) && g.isPresent()) {//check if mouse is clicked within shape

						//we can either just print out the object class name
						System.out.println("Clicked a "+g.getClass().getName());
						pPanel.setInfo(g);
						simCityGui.setControlPanel(pPanel);

						if(g instanceof MarketCashierGui) {
						AlertLog.getInstance().logInfo(AlertTag.WORLD, "Mouse", "You clicked a " + g.getClass().getSimpleName() + "!");
						}
						else if(g instanceof MarketCustomerGui) {
							AlertLog.getInstance().logInfo(AlertTag.WORLD, "Mouse", "You clicked a " + g.getClass().getSimpleName() + "!");
						}
						else if(g instanceof MarketWorkerGui) {
							AlertLog.getInstance().logInfo(AlertTag.WORLD, "Mouse", "You clicked a " + g.getClass().getSimpleName() + "!");
						}
						else if(g instanceof IdlePersonGui) {
							AlertLog.getInstance().logInfo(AlertTag.WORLD, "Mouse", "You clicked a " + g.getClass().getSimpleName() + "!");
						}

						//controlPanel.updateSelected(g.getPerson());

					}
				}
				// for (BuildingGui g : buildingGuis) {
				for(BuildingGui g : buildingGuis) {
					if (g.contains(me.getPoint())) {//check if mouse is clicked within shape

						//if (controlPanel == null)
						AlertLog.getInstance().logInfo(AlertTag.WORLD, "Mouse", "You clicked a " + g.getClass().getSimpleName() + "!");

						if (simCityGui != null) {
							simCityGui.changeDetailPane(g.getSystem().getAnimationPanel());
							simCityGui.getDetailTracePanel().hideAllAlerts();
							simCityGui.getDetailTracePanel().showAlertsWithTag(AlertTag.valueOf(g.getSystem().getName()));
							if (g.getSystem().getControlPanel() == null) {
								System.out.println("We don't have a controlPanel for this building!");


							} else {
								simCityGui.setControlPanel(g.getSystem().getControlPanel());

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
				 xOffset = xStart - (int) e.getPoint().getX();
				 yOffset = yStart - (int) e.getPoint().getY();
				 if (xOffset != 0 || yOffset != 0) {
					 dragWorld(xOffset, yOffset);
					 xOffset = 0;
					 yOffset = 0;
					 xStart = (int) e.getPoint().getX();
					 yStart = (int) e.getPoint().getY();
				 }
				 
				 //repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}

		});

		Timer timer = new Timer(10, this );
		timer.start();
	}
	
	public void dragWorld(int x, int y) {
		//System.out.println(x+", "+y);
		return;
	}

	public void paintComponent(Graphics g) {
		// compute dt, then send dt to every gui for updatePosition
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
	}

	public void updateGuis() {
		synchronized(guis) {
			for (Gui gui : guis) {

				if (gui.isPresent()) {
					gui.updatePosition();
				}
			}
		}
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
	
	public void addCar(CarGui g) {
		carGuis.add(g);
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
}
