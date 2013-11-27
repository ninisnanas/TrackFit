package project.trackfit.view;

import java.util.ArrayList;

import project.trackfit.R;
import project.trackfit.controller.ActHistoryController;
import project.trackfit.model.History;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MenuActHistory extends Activity implements OnClickListener {
	
	Context context;
	Button home;
	Button tracker;
	Button dashboard;
	Button about;
	Button profile;
	LinearLayout content;
	LinearLayout[] container;
	ImageView[] activityImage;
	LinearLayout[] textContainer;
	TextView[] distanceTime;
	TextView[] calorie;
	TextView[] date;
	Button[] buttonShare;
	ArrayList<History> listHistory;
	ActHistoryController actHistoryController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		overridePendingTransition(0, 0);
		context = this;
		actHistoryController = new ActHistoryController(context);
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		content = (LinearLayout) findViewById(R.id.content);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		
		showActivityHistory();
	}

	private void showActivityHistory() {
		listHistory = actHistoryController.getActivityHistory();
		int listSize = listHistory.size();
		container = new LinearLayout[listSize];
		activityImage = new ImageView[listSize];
		textContainer = new LinearLayout[listSize];
		distanceTime = new TextView[listSize];
		calorie = new TextView[listSize];
		date = new TextView[listSize];
		buttonShare = new Button[listSize];
		
		for (int i = 0; i < listSize; i++) {
			History history = listHistory.get(i);
			container[i] = new LinearLayout(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 10, 10, 10);
			container[i].setLayoutParams(params);
			container[i].setOrientation(LinearLayout.HORIZONTAL);
			activityImage[i] = new ImageView(context);
			params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 0, 0, 0);
			activityImage[i].setLayoutParams(params);
			textContainer[i] = new LinearLayout(context);
			textContainer[i].setOrientation(LinearLayout.VERTICAL);
			textContainer[i].setLayoutParams(params);
			distanceTime[i] = new TextView(context);
			distanceTime[i].setPadding(5, 5, 5, 5);
			distanceTime[i].setTextSize(20);
			calorie[i] = new TextView(context);
			calorie[i].setPadding(5, 5, 5, 5);
			calorie[i].setTextSize(15);
			date[i] = new TextView(context);
			date[i].setPadding(5, 5, 5, 5);
			date[i].setTextSize(15);
			buttonShare[i] = new Button(context);
			params = new LinearLayout.LayoutParams(75, 75);
			params.setMargins(10, 0, 10, 0);
			params.gravity = Gravity.CENTER;
			buttonShare[i].setLayoutParams(params);
			buttonShare[i].setOnClickListener(this);
			
			if (history.getAid() == 1) {
				activityImage[i].setBackgroundResource(R.drawable.sports_regular_biking_icon);
			} else if (history.getAid() == 2){
				activityImage[i].setBackgroundResource(R.drawable.sports_running_icon);
			} else {
				activityImage[i].setBackgroundResource(R.drawable.walking);
			}
			
			String temp = history.getDistance() + " km in " + history.getTime() + ":" + history.getTime() + ":" + history.getTime();
			distanceTime[i].setText(temp);
			temp = history.getCalorie() + " cal";
			calorie[i].setText(temp);
			temp = history.getDay() + "/" + history.getMonth() + "/" + history.getYear();
			date[i].setText(temp);
			
			buttonShare[i].setBackgroundResource(R.drawable.social_share);
			
			textContainer[i].addView(distanceTime[i]);
			textContainer[i].addView(calorie[i]);
			textContainer[i].addView(date[i]);
			
			container[i].addView(activityImage[i]);
			container[i].addView(textContainer[i]);
			container[i].addView(buttonShare[i]);
			container[i].setBackgroundResource(R.color.light_pink);
			
			content.addView(container[i]);
		}
	}
	
	public void share(History history) {
		int aid = history.getAid();
		String activity;
		
		if (aid == 1) activity = "biking";
		else if (aid == 2) activity = "running";
		else activity = "walking";
		
		int distance = history.getDistance();
		int calorie = history.getCalorie();
		String time = history.getTime() + ":" + history.getTime() + ":" +  history.getTime();
		
		Intent shareIntent = null;
		String message = "I was out "+activity+" for " + distance + " km in " + time + ". I burnt " + calorie + " cal!";
		if(message.length()<=144){
			shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		}
		startActivity(Intent.createChooser(shareIntent, "Share via :"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(home)) {
			startActivity(new Intent(getApplicationContext(), MenuHome.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
		}
		else if (v.equals(tracker)) {
			startActivity(new Intent(getApplicationContext(), MenuSportTrack.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(dashboard)) {
			startActivity(new Intent(getApplicationContext(), MenuDashDist.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(about)) {
			startActivity(new Intent(getApplicationContext(), MenuAbout.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(profile)) {
			startActivity(new Intent(getApplicationContext(), MenuProfile.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else {
			for (int i = 0; i < container.length; i++) {
				final int a = i;
				if (v.equals(buttonShare[i])) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							share(listHistory.get(a));
						}
					});
					builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					
					builder.setMessage("Do you want to share this activity?");
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		}
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
}
