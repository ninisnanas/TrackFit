package project.trackfit.controller;

import project.trackfit.model.History;
import project.trackfit.model.User;
import android.content.Context;

public class SportTrackController {
	ActivityManager activityManager;
	
	public SportTrackController(Context context) {
		activityManager = new ActivityManager(context);
	}
	
	public int getAge() {
		User user = activityManager.getUser();
		return user.getAge();
	}
	
	public int getWeight() {
		User user = activityManager.getUser();
		return user.getWeight();
	}
	
	public boolean addHistory(int uid, int aid, float distance, int hour, int minute, int second, float calorie, float speed, int day, int month, int year) {
		return activityManager.addHistory(uid, aid, distance, hour, minute, second, calorie, speed, day, month, year);
	}
}
