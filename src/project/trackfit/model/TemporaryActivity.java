package project.trackfit.model;

public class TemporaryActivity {
	private int uid;
	private int aid;
	private int distance;
	private int time;
	private int calorie;
	private int day;
	private int month;
	private int year;
	
	public TemporaryActivity(int uid, int aid, int distance, int time, int calorie, int day, int month, int year) {
		this.uid = uid;
		this.aid = aid;
		this.distance = distance;
		this.time = time;
		this.calorie = calorie;
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
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
}
