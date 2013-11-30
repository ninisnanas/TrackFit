package project.trackfit.model;

public class History {
	private int uid;
	private int aid;
	private int distance;
	private int time;
	private int calorie;
	private int speed;
	private int day;
	private int month;
	private int year;
	
	public History(int uid, int aid, int distance, int time, int calorie, int speed, int day, int month, int year) {
		this.uid = uid;
		this.aid = aid;
		this.distance = distance;
		this.time = time;
		this.calorie = calorie;
		this.speed = speed;
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getUid() {
		return uid;
	}
	
	public int getAid() {
		return aid;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public int getTime() {
		return time;
	}
	
	public int getCalorie() {
		return calorie;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
}