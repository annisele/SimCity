package simcity;

public class Clock {
	
	static long currentTime;
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
	
	public static long getTime() {
		currentTime = (System.currentTimeMillis() - startTime) / 1000;
		return currentTime;
	}
		
	public static String getDisplayTime(){
		int h = 0, m = 0, d = 0;
		String day = "";
		currentTime = (System.currentTimeMillis() - startTime) / 1000;
		m = (int)((currentTime % 6)*10);
		h = (int)((currentTime / 6)%24);
		d = (int)((currentTime / (6*24))%7);
		
		if (d == 0) day = "MON";
		else if (d == 1) day = "TUE";
		else if (d == 2) day = "WED";
		else if (d == 3) day = "THU";
		else if (d == 4) day = "FRI";
		else if (d == 5) day = "SAT";
		else if (d == 6) day = "SUN";
		
		return (day + "  "+ h + ":" + m);
	}
	
	public static void reset() {
		startTime = System.currentTimeMillis();
	}
}
