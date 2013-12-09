package simcity.buildings.transportation;

import java.util.Timer;
import java.util.concurrent.Semaphore;

import simcity.buildings.transportation.*;

public class Intersection {
		public BusAgent bus;
		public CarAgent car;
		public Semaphore lock = new Semaphore(1, true);
		Timer passtimer = new Timer();
		
	
}