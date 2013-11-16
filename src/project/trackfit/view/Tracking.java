package project.trackfit.view;

import project.trackfit.R;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;


/*
public class Tracking extends MapActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking);
		
		// set the zoom level, center point and enable the default zoom controls 
	      MapView map = (MapView) findViewById(R.id.map);
	      map.getController().setZoom(9);
	      map.getController().setCenter(new GeoPoint(38.892155,-77.036195));
	      map.setBuiltInZoomControls(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tracking, menu);
		return true;
	}
	
	// return false since no route is being displayed 
    @Override
    public boolean isRouteDisplayed() {
      return false;
    }

}
*/

public class Tracking extends MapActivity implements OnClickListener {
	Button home;
	Button history;
	Button dashboard;
	Button about;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_tracking);
	      
	    home = (Button) findViewById(R.id.HomeIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		
		home.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);

		// set the zoom level, center point and enable the default zoom controls 
		MapView map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(9);
		map.getController().setCenter(new GeoPoint(38.892155,-77.036195));
		map.setBuiltInZoomControls(true);
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(home)) {
			startActivity(new Intent(getApplicationContext(), Home.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(about)) {
			startActivity(new Intent(getApplicationContext(), About.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(history)) {
			startActivity(new Intent(getApplicationContext(), History.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
		else if (v.equals(dashboard)) {
			startActivity(new Intent(getApplicationContext(), Dashboard.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
	}
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}

    // return false since no route is being displayed 
    @Override
    public boolean isRouteDisplayed() {
    	return false;
    }
    
}
