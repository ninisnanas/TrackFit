package project.trackfit.controller;

import android.content.Context;
import project.trackfit.database.DatabaseHandler;
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
}
