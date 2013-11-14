package simcity.buildings.market;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import agent.Agent;
import simcity.Role;

public class MarketTruckAgent extends Agent implements simcity.interfaces.market.MarketTruck {

	List<TruckOrder> orders = Collections.synchronizedList(new ArrayList<TruckOrder>());

	public MarketTruckAgent() {

	}

	public void msgPleaseDeliverOrder(Role r, Map<String, Integer> items) {
		orders.add(new TruckOrder(r, items));
	}

	@Override
	protected boolean pickAndExecuteAnAction() {
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
}
