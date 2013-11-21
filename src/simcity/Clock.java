package simcity;

public class Clock {
	
	static long currentTime;
	static long startTime = System.currentTimeMillis();
	
	public Clock() {
	}
	
	public static long getTime() {
		currentTime = (System.currentTimeMillis()-startTime)/1000;
		return currentTime;
	}
}
