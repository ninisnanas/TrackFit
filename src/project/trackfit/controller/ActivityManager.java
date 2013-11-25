package project.trackfit.controller;

import java.util.ArrayList;

import project.trackfit.database.DatabaseHandler;
import project.trackfit.model.Activity;
import project.trackfit.model.History;

import android.content.Context;


public class ActivityManager {
	private DatabaseHandler db;
	private ArrayList<History> listHistory;
	private ArrayList<Activity> listActivity;
	
	public ActivityManager(Context context) {
		db = new DatabaseHandler(context);
		listHistory = db.getHistory();
		listActivity = db.getActivity();
	}
	
	public ArrayList<Activity> getListActivity() {
		return listActivity;
	}
	
	public ArrayList<History> getListHistory() {
		return listHistory;
	}
}
