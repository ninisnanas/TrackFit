package project.trackfit.view;

import java.util.ArrayList;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import project.trackfit.R;
import project.trackfit.controller.ActHistoryController;
import project.trackfit.model.History;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

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
	SocialAuthAdapter adapter;
	String messageToShare;

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
		
		adapter = new SocialAuthAdapter(new ResponseListener());
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.MYSPACE, R.drawable.myspace);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.GOOGLE, R.drawable.google);
		adapter.addProvider(Provider.GOOGLEPLUS, R.drawable.googleplus);
		//adapter.addProvider(Provider.INSTAGRAM, R.drawable.i)
		adapter.addProvider(Provider.FOURSQUARE, R.drawable.foursquare);
		
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		
		for (int i = 0; i < buttonShare.length; i++) {
			adapter.enable(buttonShare[i]);
		}
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
		
		messageToShare = "I was out "+activity+" for " + distance + " km in " + time + ". I burnt " + calorie + " cal!";
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
		} 
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
	
	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(MenuActHistory.this, providerName + " connected", Toast.LENGTH_LONG).show();

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.

			adapter.updateStatus(messageToShare, new MessageListener(), false);
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}
	}
	
	// To get status of message after authentication
		private final class MessageListener implements SocialAuthListener<Integer> {
			@Override
			public void onExecute(String provider, Integer t) {
				Integer status = t;
				if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
					Toast.makeText(MenuActHistory.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MenuActHistory.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(SocialAuthError e) {

			}
		}
}
