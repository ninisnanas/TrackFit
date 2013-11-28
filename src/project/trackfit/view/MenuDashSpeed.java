package project.trackfit.view;

import project.trackfit.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuDashSpeed extends Activity implements OnClickListener {
	
	Button home;
	Button tracker;
	Button history;
	Button about;
	Button profile;
	Button distance;
	Button calorie;
	Button time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_speed);
		overridePendingTransition(0,0);
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		distance = (Button) findViewById(R.id.buttonDistance);
		calorie = (Button) findViewById(R.id.buttonCalories);
		time = (Button) findViewById(R.id.buttonTime);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		distance.setOnClickListener(this);
		calorie.setOnClickListener(this);
		time.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dash_speed, menu);
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
		} else if (v.equals(time)) {
			startActivity(new Intent(getApplicationContext(), MenuDashTime.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
	}

}
