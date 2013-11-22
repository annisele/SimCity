package simcity;

public class Clock {
	
	static long currentTime;
	static long startTime = System.currentTimeMillis();
	public class time{
		int day;
		int hour;
		int min;
		time(int h,int m){
			hour=h;
			min=m;
		}
	}
	time worldTime= new time(0,0);
	public Clock() {
	}
	
	public static long getTime() {
		currentTime = (System.currentTimeMillis()-startTime)/1000;
		return currentTime;
	}
	
	
	public void updateWorldTime(){
		int hr=0, mn=0, d=0;
		currentTime = (System.currentTimeMillis()-startTime)/1000;
		if(currentTime>60){
			hr=(int)currentTime/60;
			if (hr>24){
				d=hr/24;
				hr=hr%24;
			}
			mn=(int)currentTime%60;
		}
		else{
			mn=(int)currentTime;
		}
		worldTime.day=d;
		worldTime.hour=hr;
		worldTime.min=mn;
	}
}
