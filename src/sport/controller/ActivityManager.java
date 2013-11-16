package sport.controller;

import java.util.ArrayList;

import android.content.Context;

import sport.database.DatabaseHandler;
import sport.model.Activity;
import sport.model.History;

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
