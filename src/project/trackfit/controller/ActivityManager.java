package project.trackfit.controller;

import java.util.ArrayList;

import project.trackfit.database.DatabaseHandler;
import project.trackfit.model.Activity;
import project.trackfit.model.History;
import project.trackfit.model.User;

import android.content.Context;


public class ActivityManager {
	private static DatabaseHandler db;
	private static ArrayList<History> listHistory;
	private static ArrayList<Activity> listActivity;
	private static User user;
	
	public ActivityManager(Context context) {
		db = new DatabaseHandler(context);
		listHistory = db.getHistory();
		listActivity = db.getActivity();
		user = db.getUser();
	}
	
	public ArrayList<Activity> getListActivity() {
		return listActivity;
	}
	
	public ArrayList<History> getListHistory() {
		return listHistory;
	}
	
	public User getUser() {
		return user;
	}

	public boolean addHistory(int uid, int aid, float distance, int hour, int minute, int second, float calorie, float speed, int day, int month, int year) {
		// TODO Auto-generated method stub
		return db.insertHistory(uid, aid, distance, hour, minute, second, calorie, speed, day, month, year);
	}
}
