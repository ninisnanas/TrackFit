package project.trackfit.view;

import java.util.ArrayList;
import java.util.List;

import project.trackfit.R;
//import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MenuSportTrack extends Activity implements OnClickListener {
	List<LatLng> routePoints;
	
	protected GoogleMap map;

	int invisible = View.GONE;
	int visible = View.VISIBLE;

	private TextView stopwatch;
	private Button home;
	private Button history;
	private Button dashboard;
	private Button about;
	private Button profile;
	private Button start;
	private Button pause;
	private Button resume;
	private Button stop;

	long timeInMilliseconds = 0L;
	long startTime = 0L;
	long updatedTime = 0L;
	long timeSwapBuff = 0L;
	long time = 0L;

	Handler timerHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport_track);
				
		setupView();
		setupEvent();
		
		try {
			initializeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void setupView(){
		stopwatch = (TextView) findViewById(R.id.textViewDuration);
		home = (Button) findViewById(R.id.HomeIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		start = (Button) findViewById(R.id.buttonStart);
		pause = (Button) findViewById(R.id.buttonPause);
		resume = (Button) findViewById(R.id.buttonResume);
		stop = (Button) findViewById(R.id.buttonStop);
		
		start.setVisibility(visible);
		pause.setVisibility(invisible);
		resume.setVisibility(invisible);
		stop.setVisibility(invisible);
	}
	
	private void setupEvent(){
		home.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		start.setOnClickListener(this);
		pause.setOnClickListener(this);
		resume.setOnClickListener(this);
		stop.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(home)) {
			startActivity(new Intent(getApplicationContext(), MenuHome.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
							| Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
		} else if (v.equals(about)) {
			startActivity(new Intent(getApplicationContext(), MenuAbout.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(history)) {
			startActivity(new Intent(getApplicationContext(),
					MenuActHistory.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(dashboard)) {
			startActivity(new Intent(getApplicationContext(),
					MenuDashDist.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(profile)) {
			startActivity(new Intent(getApplicationContext(), MenuProfile.class)
					.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(start)) {
			start.setVisibility(invisible);
			pause.setVisibility(visible);
			resume.setVisibility(invisible);
			stop.setVisibility(visible);
			startTime = SystemClock.uptimeMillis();
			timerHandler.postDelayed(timerRunnable, 0);
		} else if (v.equals(pause)) {
			start.setVisibility(invisible);
			pause.setVisibility(invisible);
			resume.setVisibility(visible);
			stop.setVisibility(invisible);
			timeSwapBuff += timeInMilliseconds;
			timerHandler.removeCallbacks(timerRunnable);
		} else if (v.equals(resume)) {
			start.setVisibility(invisible);
			pause.setVisibility(visible);
			resume.setVisibility(invisible);
			stop.setVisibility(visible);
			startTime = SystemClock.uptimeMillis();
			timerHandler.postDelayed(timerRunnable, 0);
		} else if (v.equals(stop)) {
			start.setVisibility(visible);
			pause.setVisibility(invisible);
			resume.setVisibility(invisible);
			stop.setVisibility(invisible);
			timeSwapBuff = 0;
			timerHandler.removeCallbacks(timerRunnable);
			Toast.makeText(getApplicationContext(), "" + updatedTime + "haha",
					Toast.LENGTH_LONG).show();

		}
	}
	
	//calculate distance between 2 point
	/*
	private float calculateDistance(GeoPoint startPoint, GeoPoint endPoint){
		float[] result = new float[1];
		Location.distanceBetween(startPoint.getLatitude(), startPoint.getLongitude(), 
				endPoint.getLatitude(), endPoint.getLongitude(), result);
		return result[0];
	}
	*/

	
	// enable features of the overlay
	@Override
    protected void onResume() {
        super.onResume();
        initializeMap();
    }

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
		
	}
	
	private Runnable timerRunnable = new Runnable() {
		public void run() {
			
			//run a timer
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			int hours = mins / 60;
			secs = secs % 60;
			int milliSecs = (int) (updatedTime % 1000);
			stopwatch.setText("" + hours + ":" + String.format("%02d", mins)
					+ ":" + String.format("%02d", secs) + ":"
					+ String.format("%02d", milliSecs));
			timerHandler.postDelayed(this, 0);
			
			//draw tracking lines
			
			if (secs % 5 == 0) {
				
				LatLng currentLocation = new LatLng(map.getMyLocation().getLatitude(),
						map.getMyLocation().getLongitude());
				Log.d("test", "haha1");
				routePoints.add(currentLocation);
				Log.d("test", "haha2");
				Polyline route = map.addPolyline(new PolylineOptions().geodesic(true));
				Log.d("test", "haha3");
				route.setPoints(routePoints);
				Log.d("test", "haha4");
				
				
			}
			
		}
	};
	
	private void initializeMap() {

		if (map == null) {
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			map.setMyLocationEnabled(true);

			double latitude = map.getMyLocation().getLatitude();
			double longitude = map.getMyLocation().getLongitude();

			MarkerOptions marker = new MarkerOptions().position(
					new LatLng(latitude, longitude)).title("My Position").snippet("Rumah");
			map.addMarker(marker);

			if (map == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

}
