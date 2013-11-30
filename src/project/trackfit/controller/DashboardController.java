package project.trackfit.controller;

import java.util.ArrayList;

import project.trackfit.model.History;

import android.content.Context;

public class DashboardController {
	ActivityManager activityManager;
	
	public DashboardController(Context context) {
		activityManager = new ActivityManager(context);
	}
	
	public ArrayList<History> getHistoryList() {
		return activityManager.getListHistory();
	}
}
