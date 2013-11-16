package sport.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity implements OnClickListener {
	Button tracker;
	Button history;
	Button dashboard;
	Button about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
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
		if (v.equals(tracker)) startActivity(new Intent(getApplicationContext(), Tracking.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(history)) startActivity(new Intent(getApplicationContext(), History.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(dashboard)) startActivity(new Intent(getApplicationContext(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
		else if (v.equals(about)) startActivity(new Intent(getApplicationContext(), About.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
	}

}
