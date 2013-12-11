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
	HOUSE4,
	HOUSE5,
	HOUSE6,
	HOUSE7,
	HOUSE8,
	HOUSE9,
	HOUSE10,
	HOUSE11,
	HOUSE12,
	HOUSE13,
	HOUSE14,
	HOUSE15,
	HOUSE16,
	HOUSE17,
	HOUSE18,
	HOUSE19,
	HOUSE20,
	HOUSE21,
	HOUSE22,
	HOUSE23,
	HOUSE24,
	HOUSE25,
	HOUSE26,
	HOUSE27,
	HOUSE28,
	HOUSE29,
	HOUSE30,
	HOUSE31,
	HOUSE32,
	HOUSE33,
	HOUSE34,
	HOUSE35,
	HOUSE36,
	HOUSE37,
	HOUSE38,
	HOUSE39,
	HOUSE40,
	HOUSE41,
	HOUSE42,
	HOUSE43,
	HOUSE44,
	HOUSE45,
	HOUSE46,
	HOUSE47,
	HOUSE48,
	HOUSE49,
	HOUSE50
}
