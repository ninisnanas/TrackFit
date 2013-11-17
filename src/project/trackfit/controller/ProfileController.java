package project.trackfit.controller;

import android.content.Context;
import project.trackfit.database.DatabaseHandler;
import project.trackfit.model.User;

public class ProfileController {
	DatabaseHandler db;
	User user;
	
	public ProfileController(Context context) {
		db = new DatabaseHandler(context);
	}
	
	public User getUser() {
		return db.getUser();
	}
	
	public boolean setUser(int id, String name, int age, int weight, int height, double body_mass) {
		return db.createNewUser(id, name, height, weight, age, body_mass);
	}
	
	public void editUser(int id, String name, int age, int weight, int height, double body_mass) {
		db.editUser(id, name, height, weight, age, body_mass);
	}
	
	public double calculateBodyMass(int weight, int height) {
		double heightInMeter = (double) (height * 0.01);
		return (weight / (heightInMeter * heightInMeter));
	}
	
	public String getBMIDesc(double BMI) {
		if (BMI < 15) {
			return "very severely underweight";
		} else if (BMI <= 16) {
			return "severely underweight";
		} else if (BMI <= 18.5) {
			return "underweight";
		} else if (BMI <= 25) {
			return "normal (healthy weight)";
		} else if (BMI <= 30) {
			return "overweight";
		} else if (BMI <= 35) {
			return "obese class I (moderately obese)";
		} else if (BMI <= 40) {
			return "obese class II (severely obese)";
		} else {
			return "obese class III (very severely obese)";
		}
	}
}
