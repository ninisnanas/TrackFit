package project.trackfit.database;

import java.util.ArrayList;

import project.trackfit.model.Activity;
import project.trackfit.model.History;
import project.trackfit.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "TrackFitDatabase";
	private static final int DATABASE_VERSION = 1;

	/**
	 * Constructor
	 * @param context context dari activity
	 */
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USER = "CREATE TABLE USER (id integer primary key, name text, height integer, weight integer, age integer, body_mass real)";
		String CREATE_ACTIVITY = "CREATE TABLE ACTIVITY (id integer primary key, name text)";
		String INSERT_ACTIVITY = "INSERT INTO ACTIVITY VALUES (1, 'Walking')";
		String INSERT_ACTIVITY2 = "INSERT INTO ACTIVITY VALUES (2, 'Running')";
		String INSERT_ACTIVITY3 = "INSERT INTO ACTIVITY VALUES (3, 'Cycling')";
		String CREATE_HISTORY = "CREATE TABLE HISTORY (uid integer, aid integer, distance real, hour integer, minute integer, second integer, calorie real, speed real, day integer, month integer, year integer)";
		//String INSERT_DUMMY_HISTORY = "INSERT INTO HISTORY VALUES (1, 1, 10, 1, 1, 1, 1, 1, 1, 1, 2013)";
		//String INSERT_DUMMY_HISTORY1 = "INSERT INTO HISTORY VALUES (1, 2, 20, 2, 2, 2, 2, 2, 2, 2, 2013)";
		//String INSERT_DUMMY_HISTORY2 = "INSERT INTO HISTORY VALUES (1, 1, 30, 3, 3, 3, 3, 3, 3, 3, 2013)";
		//String INSERT_DUMMY_HISTORY3 = "INSERT INTO HISTORY VALUES (1, 2, 40, 4, 4, 4, 4, 4, 4, 4, 2013)";
		//String INSERT_DUMMY_HISTORY4 = "INSERT INTO HISTORY VALUES (1, 1, 50, 5, 5, 5, 5, 5, 5, 5, 2013)";
		//String INSERT_DUMMY_HISTORY5 = "INSERT INTO HISTORY VALUES (1, 2, 60, 6, 6, 6, 6, 6, 6, 6, 2013)";
		String CREATE_USER_PREFERENCE = "CREATE TABLE USER_PREFERENCE (voice_command boolean)";
		
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_ACTIVITY);
		db.execSQL(INSERT_ACTIVITY);
		db.execSQL(INSERT_ACTIVITY2);
		db.execSQL(INSERT_ACTIVITY3);
		db.execSQL(CREATE_HISTORY);
		//db.execSQL(INSERT_DUMMY_HISTORY);
		//db.execSQL(INSERT_DUMMY_HISTORY1);
		//db.execSQL(INSERT_DUMMY_HISTORY2);
		//db.execSQL(INSERT_DUMMY_HISTORY3);
		//db.execSQL(INSERT_DUMMY_HISTORY4);
		//db.execSQL(INSERT_DUMMY_HISTORY5);
		db.execSQL(CREATE_USER_PREFERENCE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	public boolean checkIfUserExist() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
		
		if (cursor.getCount() !=0 ){
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}
	
	public boolean createNewUser(int id, String name, int height, int weight, int age, double body_mass) {
		boolean success = true;
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put("id", id);
		value.put("name", name);
		value.put("height", height);
		value.put("weight", weight);
		value.put("age", age);
		value.put("body_mass", body_mass);
		
		try {
			db.insertOrThrow("USER", null, value);
		} catch (SQLiteConstraintException e) {
			success = false;
		}
		
		db.close();
		return success;
	}
	
	public void editUser(int id, String name, int height, int weight, int age, double body_mass) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put("name", name);
		value.put("height", height);
		value.put("weight", weight);
		value.put("age", age);
		value.put("body_mass", body_mass);
		
		db.update("USER", value, "id = " + id, null);
		db.close();
	}
	
	public boolean insertHistory(int uid, int aid, float distance, int hour, int minute, float second, float calorie, float speed, int day, int month, int year) {
		boolean success = true;
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues value = new ContentValues();
		value.put("uid", uid);
		value.put("aid", aid);
		value.put("distance", distance);
		value.put("hour", hour);
		value.put("minute", minute);
		value.put("second", second);
		value.put("calorie", calorie);
		value.put("speed", speed);
		value.put("day", day);
		value.put("month", month);
		value.put("year", year);
		
		try {
			db.insertOrThrow("HISTORY", null, value);
		} catch (SQLiteConstraintException e) {
			success = false;
		}
		
		db.close();
		return success;
	}
	
	public ArrayList<History> getHistory() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<History> listHistory = new ArrayList<History>();
		History temp;
		int uid;
		int aid;
		float distance;
		int hour;
		int minute;
		int second;
		float calorie;
		float speed;
		int day;
		int month;
		int year;
		
		Cursor cursor = db.rawQuery("SELECT * FROM HISTORY", null);
		
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			uid = cursor.getInt(0);
			aid = cursor.getInt(1);
			distance = cursor.getFloat(2);
			hour = cursor.getInt(3);
			minute = cursor.getInt(4);
			second = cursor.getInt(5);
			calorie = cursor.getFloat(6);
			speed = cursor.getFloat(7);
			day = cursor.getInt(8);
			month = cursor.getInt(9);
			year = cursor.getInt(10);
			
			temp = new History(uid, aid, distance, hour, minute, second, calorie, speed, day, month, year);
			
			listHistory.add(temp);
		}
		
		cursor.close();
		db.close();
		
		return listHistory;
	}
	
	public ArrayList<Activity> getActivity() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Activity> listActivity = new ArrayList<Activity>();
		Activity temp;
		int id;
		String name;
		
		Cursor cursor = db.rawQuery("SELECT * FROM ACTIVITY", null);
		
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			id = cursor.getInt(0);
			name = cursor.getString(1);
			
			temp = new Activity(id, name);
			
			listActivity.add(temp);
		}
		
		cursor.close();
		db.close();
		
		return listActivity;
	}
	
	public User getUser() {
		SQLiteDatabase db = this.getReadableDatabase();
		int id;
		String name;
		int height;
		int weight;
		int age;
		double body_mass;
		
		Cursor cursor = db.rawQuery("SELECT * FROM USER", null);
		
		cursor.moveToPosition(0);
		id = cursor.getInt(0);
		name = cursor.getString(1);
		height = cursor.getInt(2);
		weight = cursor.getInt(3);
		age = cursor.getInt(4);
		body_mass = cursor.getInt(5);
		
		User user = new User(id, name, height, weight, age, body_mass);
			
		cursor.close();
		db.close();
		
		return user;
	}
}
