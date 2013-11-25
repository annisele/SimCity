package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import simcity.Role;
import simcity.interfaces.market.MarketTruck;
import agent.Agent;

public class MarketTruckAgent extends Agent implements MarketTruck {

	private List<TruckOrder> orders = Collections.synchronizedList(new ArrayList<TruckOrder>());

	public MarketTruckAgent() {

	}

	@Override
	public void msgPleaseDeliverOrder(Role r, Map<String, Integer> items) {
		orders.add(new TruckOrder(r, items));
	}

	@Override
	public boolean pickAndExecuteAnAction() {
		synchronized(orders) {
			if(!orders.isEmpty()) {
				DeliverOrder(orders.get(0));
				return true;
			}
		}
		return false;
	}

	private void DeliverOrder(TruckOrder o) {
		//DoDeliverOrder(o.role.person.getAddress()); //animation call
		//use semaphores to wait until animation arrives at address
		if(o.role instanceof MarketCustomerRole) {
			((MarketCustomerRole) o.role).msgDeliveringOrder(o.itemsToDeliver);
		}
		else {
			//need to implement delivering to the 6 different restaurant cooks
		}
		orders.remove(o);
	}

	private class TruckOrder {
		Role role;
		Map<String, Integer> itemsToDeliver;

		TruckOrder(Role roleIn, Map<String, Integer> itemsToDeliverIn) {
			role = roleIn;
			itemsToDeliver = itemsToDeliverIn;
		}

	}

	@Override
	public void atDestination() {
		// TODO Auto-generated method stub

	}
}
