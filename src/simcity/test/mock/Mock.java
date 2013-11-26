package simcity.test.mock;

import simcity.test.mock.EventLog;

/**
 * This is the base class for all mocks.
 *
 * @author Levonne Key
 *
 */
public class Mock {
	private String name;

	public Mock(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public EventLog log = new EventLog();

	public String toString() {
		return this.getClass().getName() + ": " + name;
	}

}
