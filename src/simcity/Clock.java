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
	
	public Clock() {
		
	}
	
	public static int getTime() {
		currentTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
		return currentTime;
	}
		
	public static String getDay() {
		int d = 0;
		String day = "";
		currentTime = (int) ((System.currentTimeMillis() - startTime) / 1000);

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
		currentTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
		
		h = (int)(((currentTime / 6)+5)%24+1);

		return h;
	}
	
	public static String getDisplayTime(){
		int h = 0, m = 0;
		currentTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
		
		m = (int)((currentTime % 6)*10);
		h = (int)(((currentTime / 6)+5)%24+1);
		
		String min = ""+m;
		String hr = ""+h;
		String ampm = "am";
		int hi = 0;
		if (m == 0)
			min = "00";
		if (h > 12) //{
			ampm = "pm";
		
		String time = (getDay() + "  "+ h + ":" + min + "  " + ampm);
		
		return time;
	}
	
	public static void reset() {
		startTime = System.currentTimeMillis();
	}
}
