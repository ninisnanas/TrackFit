package project.trackfit.controller;

import java.util.ArrayList;

import android.content.Context;
import project.trackfit.database.DatabaseHandler;
import project.trackfit.model.History;
import project.trackfit.model.User;

public class HomeController {
	DatabaseHandler db;
	
	public HomeController(Context context) {
		db = new DatabaseHandler(context);
	}
	
	public boolean checkIfUserExist() {
		return db.checkIfUserExist();
	}
	
	public User getUser() {
		return db.getUser();
	}
	
	public ArrayList<History> getListHistory() {
		return db.getHistory();
	}
	
	public History getLastHistory() {
		ArrayList<History> hist = getListHistory();
		return hist.get(hist.size() - 1);
	}
	
	public boolean checkHistory() {
		ArrayList<History> hist = getListHistory();
		if (hist.size() != 0) return true;
		else return false;
	}
}
