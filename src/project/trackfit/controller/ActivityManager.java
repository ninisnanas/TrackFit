package project.trackfit.controller;

import java.util.ArrayList;

import project.trackfit.database.DatabaseHandler;
import project.trackfit.model.Activity;
import project.trackfit.model.History;

import android.content.Context;


public class ActivityManager {
	DatabaseHandler db;
	ArrayList<History> listHistory;
	ArrayList<Activity> listActivity;
	
	public ActivityManager(Context context) {
		db = new DatabaseHandler(context);
	}
	
	public ArrayList<Activity> getListActivity() {
		return db.getActivity();
	}
	
	public ArrayList<History> getListHistory() {
		return db.getHistory();
	}
}
