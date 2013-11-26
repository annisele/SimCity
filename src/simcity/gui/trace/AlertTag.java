package simcity.gui.trace;

/**
 * These enums represent tags that group alerts together.  <br><br>
 * 
 * This is a separate idea from the {@link AlertLevel}.
 * A tag would group all messages from a similar source.  Examples could be: BANK_TELLER, RESTAURANT_ONE_WAITER,
 * or PERSON.  This way, the trace panel can sort through and identify all of the alerts generated in a specific group.
 * The trace panel then uses this information to decide what to display, which can be toggled.  You could have all of
 * the bank tellers be tagged as a "BANK_TELLER" group so you could turn messages from tellers on and off.
 * 
 * @author Keith DeRuiter
 *
 */
public enum AlertTag {
	WORLD,
	MARKET1,
	MARKET2,
	MARKET3,
	BANK1,
	BANK2,
	BANK3,
	BUS_STOP,
	RESTAURANT1,		
	RESTAURANT2,
	RESTAURANT3,
	RESTAURANT4,		
	RESTAURANT5,
	RESTAURANT6,
	HOUSE1,
	HOUSE2,
	HOUSE3,
	HOUSE4
}
