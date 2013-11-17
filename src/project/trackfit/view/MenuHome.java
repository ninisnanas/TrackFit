package project.trackfit.view;

import project.trackfit.R;
import project.trackfit.controller.HomeController;
import project.trackfit.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuHome extends Activity implements OnClickListener {
	Button tracker;
	Button history;
	Button dashboard;
	Button about;
	TextView userName;
	Context context;
	HomeController homeController;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		context = this;
		
		homeController = new HomeController(this);
		
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		
		userName = (TextView) findViewById(R.id.textViewUserName);
		
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		
		if (checkUser()) {
			// User has created a profile, load last activity
			user = homeController.getUser();
			userName.setText("Hello, " + user.getName() + "!");
		} else {
			// User needs to fill profile first
			createProfile(context);
		}
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
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
}
