package project.trackfit.controller;

import java.util.ArrayList;

import project.trackfit.model.History;

import android.content.Context;

public class ActHistoryController {
	ActivityManager activityManager;
	
	public ActHistoryController(Context context) {
		activityManager = new ActivityManager(context);
	}
	
	public ArrayList<History> getActivityHistory() {
		return activityManager.getListHistory();
	}
}
