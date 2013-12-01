package project.trackfit.view;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;

import project.trackfit.R;
import project.trackfit.controller.HomeController;
import project.trackfit.model.History;
import project.trackfit.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class MenuHome extends Activity implements OnClickListener {
	Button tracker;
	Button history;
	Button dashboard;
	Button about;
	Button profile;
	LinearLayout container;
	ImageView activityImage;
	LinearLayout textContainer;
	TextView distanceTime;
	TextView calorie;
	TextView date;
	TextView homeActivity;
	Button buttonShare;

	TextView userName;
	Context context;
	HomeController homeController;
	User user;
	History recentHist;
	
	SocialAuthAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		overridePendingTransition(0,0);
		context = this;
		
		homeController = new HomeController(this);
		
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		container = (LinearLayout) findViewById(R.id.container);
		homeActivity = (TextView) findViewById(R.id.home_activity);
		
		userName = (TextView) findViewById(R.id.textViewUserName);
		
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		
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
		
		if (checkUser()) {
			// User has created a profile, load last activity
			loadLastActivity();
		} else {
			// User needs to fill profile first
			createProfile(context);
		}
	}

	private void loadLastActivity() {
		// TODO Auto-generated method stub
		user = homeController.getUser();
		userName.setText("Hello, " + user.getName() + "!");
		
		if (hasActivity()) {
			recentHist = homeController.getLastHistory();
			updateLastActivity(recentHist);
		} else {
			updateLastActivity(null);
		}
	}
	
	public String share(History history) {
		int aid = history.getAid();
		String activity;
		
		if (aid == 1) activity = "biking";
		else if (aid == 2) activity = "running";
		else activity = "walking";
		
		int distance = history.getDistance();
		int calorie = history.getCalorie();
		String time = history.getTime() + ":" + history.getTime() + ":" +  history.getTime();
		
		return "I was out "+ activity +" for " + distance + " km in " + time + ". I burnt " + calorie + " cal! #TrackFit";
	}
	
	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			final String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(MenuHome.this, providerName + " connected", Toast.LENGTH_LONG).show();

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.
			adapter.updateStatus(share(recentHist), new MessageListener(), false);
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
					Toast.makeText(MenuHome.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
				else
					Toast.makeText(MenuHome.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(SocialAuthError e) {

			}
		}

	private void updateLastActivity(History recentHist) {
		// TODO Auto-generated method stub
		if (recentHist != null) {
			homeActivity.setText("Your last activity:");
			activityImage = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(10, 0, 0, 0);
			params.gravity = Gravity.CENTER;
			activityImage.setLayoutParams(params);
			textContainer = new LinearLayout(context);
			textContainer.setOrientation(LinearLayout.VERTICAL);
			textContainer.setPadding(5, 5, 5, 5);
			textContainer.setLayoutParams(params);
			distanceTime = new TextView(context);
			distanceTime.setPadding(5, 5, 5, 5);
			distanceTime.setTextSize(20);
			calorie = new TextView(context);
			calorie.setPadding(5, 5, 5, 5);
			calorie.setTextSize(15);
			date = new TextView(context);
			date.setPadding(5, 5, 5, 5);
			date.setTextSize(15);
			buttonShare = new Button(context);
			buttonShare.setBackgroundResource(R.drawable.social_share);
			buttonShare.setLayoutParams(params);
			adapter.enable(buttonShare);
			
			if (recentHist.getAid() == 1) {
				activityImage.setBackgroundResource(R.drawable.sports_regular_biking_icon);
			} else if (recentHist.getAid() == 2){
				activityImage.setBackgroundResource(R.drawable.sports_running_icon);
			} else {
				activityImage.setBackgroundResource(R.drawable.walking);
			}
			
			String temp = recentHist.getDistance() + " km in " + recentHist.getTime() + ":" + recentHist.getTime() + ":" + recentHist.getTime();
			distanceTime.setText(temp);
			temp = recentHist.getCalorie() + " cal";
			calorie.setText(temp);
			temp = recentHist.getDay() + "/" + recentHist.getMonth() + "/" + recentHist.getYear();
			date.setText(temp);
			
			textContainer.addView(distanceTime);
			textContainer.addView(calorie);
			textContainer.addView(date);
			
			container.addView(activityImage);
			container.addView(textContainer);
			container.addView(buttonShare);
			
		} else {
			homeActivity.setText(R.string.home_no_activity);
		}
	}

	private boolean hasActivity() {
		// TODO Auto-generated method stub
		return homeController.checkHistory();
	}

	private void createProfile(Context context) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getApplicationContext(), MenuProfile.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.putExtra("newUser", true);
				startActivity(intent);
				}
			});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		builder.setMessage("You haven't created a profile! Create now?");
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private boolean checkUser() {
		// Check User Status
		return homeController.checkIfUserExist();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(tracker)) startActivity(new Intent(getApplicationContext(), MenuSportTrack.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(history)) startActivity(new Intent(getApplicationContext(), MenuActHistory.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(dashboard)) startActivity(new Intent(getApplicationContext(), MenuDashDist.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(about)) startActivity(new Intent(getApplicationContext(), MenuAbout.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(profile)) startActivity(new Intent(getApplicationContext(), MenuProfile.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

	public void onBackPressed() {
		loadLastActivity();
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
	
	/*
	public void onResume() {
		loadLastActivity();
		super.onResume();
	}*/
}
