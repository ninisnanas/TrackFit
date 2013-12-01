package project.trackfit.model;

public class History {
	private int uid;
	private int aid;
	private float distance;
	private int hour;
	private int minute;
	private int second;
	private float calorie;
	private float speed;
	private int day;
	private int month;
	private int year;
	
	public History(int uid, int aid, float distance, int hour, int minute, int second, float calorie, float speed, int day, int month, int year) {
		this.uid = uid;
		this.aid = aid;
		this.distance = distance;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
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
	
	public float getDistance() {
		return distance;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public int getSecond() {
		return second;
	}
	
	public float getCalorie() {
		return calorie;
	}
	
	public float getSpeed() {
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