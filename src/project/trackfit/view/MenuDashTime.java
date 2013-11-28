package project.trackfit.view;

import project.trackfit.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuDashTime extends Activity implements OnClickListener {
	
	Button home;
	Button tracker;
	Button history;
	Button about;
	Button profile;
	Button distance;
	Button calorie;
	Button speed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_time);
		overridePendingTransition(0,0);
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		distance = (Button) findViewById(R.id.buttonDistance);
		calorie = (Button) findViewById(R.id.buttonCalories);
		speed = (Button) findViewById(R.id.buttonSpeed);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		distance.setOnClickListener(this);
		calorie.setOnClickListener(this);
		speed.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dash_time, menu);
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
		else if (v.equals(history)) {
			startActivity(new Intent(getApplicationContext(), MenuActHistory.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(about)) {
			startActivity(new Intent(getApplicationContext(), MenuAbout.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(profile)) {
			startActivity(new Intent(getApplicationContext(), MenuProfile.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(distance)) {
			startActivity(new Intent(getApplicationContext(), MenuDashDist.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(calorie)) {
			startActivity(new Intent(getApplicationContext(), MenuDashCal.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(speed)) {
			startActivity(new Intent(getApplicationContext(), MenuDashSpeed.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
	}

}
