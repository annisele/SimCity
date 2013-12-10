package simcity;

public class Clock {
	
	static int currentTime;
	static long startTime = System.currentTimeMillis();
	
	public class time{
		int day;
		int hour;
		int min;
		time(int h, int m) {
			hour = h; 
			min = m;
		}
	}
	time worldTime= new time(0, 0);
	
	static int tenMinuteLength = 2000;
	static int hourLength = 2000*6;
	
	public Clock() {
		
	}
	
	public static int getTime() {
		currentTime = (int) ((System.currentTimeMillis() - startTime) / tenMinuteLength);
		return currentTime;
	}
	
	public static int getScheduleTime(int time) {
		// This function takes time units for a time, and returns it as that time (of the current day)
		int scheduleTime = time;
		//24*6 units is a day;
		scheduleTime += ( getTime() - ( getTime() % (6*24) ) );
		
		return scheduleTime;
	}
	
	public static int getScheduleTime(int clockHour, int clockMin) { // 8.00 => int 8*6
		// 8.39  => clockMin -> 3
		// This function takes an hour and a minute and returns it as that time (of the current day) in our time units
		int scheduleTime = (clockHour * 6)+(clockMin / 10);
		//24*6 units is a day;
		scheduleTime += ( getTime() - ( getTime() % (6*24) ) );
		
		return scheduleTime;
	}
		
	public static String getDayString() {
		int d = 0;
		String day = "";
		currentTime = (int) ((System.currentTimeMillis() - startTime) / tenMinuteLength);

		d = (int)((currentTime / (6*24))%7);
		
		if (d == 0) day = "MON";
		else if (d == 1) day = "TUE";
		else if (d == 2) day = "WED";
		else if (d == 3) day = "THU";
		else if (d == 4) day = "FRI";
		else if (d == 5) day = "SAT";
		else if (d == 6) day = "SUN";
		
		return day;
	}
	
	
	public static int getHour() {
		int h = 0;
		currentTime = (int) ((System.currentTimeMillis() - startTime) / hourLength);
		
		h = (int)(((currentTime / 6)+5)%24+1);

		return h;
	}
	
	public static int getTenMinutes() {
		int t = 0;
		currentTime = (int) ((System.currentTimeMillis() - startTime) / tenMinuteLength);
		
		t = (int)(((currentTime / 6)+5)%24+1);

		return t;
	}
	
	public static int hoursInMillis(int hours) {
		// Our current standard is 1 hour = 2000 ms
		return (hours * hourLength);
	}
	
	public static int tenMinutesInMillis(int tenMinutes) {
		// Our current standard is 1 hour = 2000 ms
		return (tenMinutes * tenMinuteLength);
	}
	
	public static String getDisplayTime(){
		int h = 0, m = 0;
		currentTime = (int) ((System.currentTimeMillis() - startTime) / tenMinuteLength);
		
		m = (int)((currentTime % 6)*10);
		h = (int)(((currentTime / 6)+5)%24+1);
		
		String min = ""+m;
		String hr = ""+h;
		String ampm = "am";
		int hi = 0;
		if (m == 0)
			min = "00";
		if (h > 11) {
			ampm = "pm";
		}
		if (h >= 13) {
			h = h-12;
		}
		
		String time = (getDayString() + "  "+ h + ":" + min + "  " + ampm);
		
		return time;
	}
	
	public static String getDisplayTime(int t){
		int h = 0, m = 0;
		currentTime = t;
		
		m = (int)((currentTime % 6)*10);
		h = (int)(((currentTime / 6)+5)%24+1);
		
		String min = ""+m;
		String hr = ""+h;
		String ampm = "am";
		int hi = 0;
		if (m == 0)
			min = "00";
		if (h > 11) {
			ampm = "pm";
		}
		if (h >= 13) {
			h = h-12;
		}
		
		String time = (getDayString() + "  "+ h + ":" + min + "  " + ampm);
		
		return time;
	}
	
	public static String getDebugTime(){
		return (getDisplayTime() + " " + getTime());
	}
	
	public static String getDebugTime(int t){
		return (getDisplayTime(t) + " " + t);
	}
	
	public static void reset() {
		startTime = System.currentTimeMillis();
	}
}
