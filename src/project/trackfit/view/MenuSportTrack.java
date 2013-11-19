package project.trackfit.view;

import java.util.ArrayList;
import java.util.List;

import project.trackfit.R;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapquest.android.maps.GeoPoint;
import com.mapquest.android.maps.LineOverlay;
import com.mapquest.android.maps.MapActivity;
import com.mapquest.android.maps.MapView;
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.RouteManager;

public class MenuSportTrack extends MapActivity implements OnClickListener {
	protected MapView map;
	private MyLocationOverlay myLocationOverlay;
	List<GeoPoint> trackedPoint;

	int invisible = View.GONE;
	int visible = View.VISIBLE;

	TextView stopwatch;
	Button home;
	Button history;
	Button dashboard;
	Button about;
	Button profile;
	Button start;
	Button pause;
	Button resume;
	Button stop;

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
		map = (MapView) findViewById(R.id.map);
		trackedPoint = new ArrayList<GeoPoint>();
		
		setupView();
		setupEvent();
		setupMapView();
		setupMyLocation();
		// displayRoute();

		// set the zoom level, center point and enable the default zoom controls

		// map.getController().setZoom(9);
		// map.getController().setCenter(new GeoPoint(38.892155, -77.036195));
		// map.setBuiltInZoomControls(true);
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
			timeSwapBuff += timeInMilliseconds;
			timerHandler.removeCallbacks(timerRunnable);
			Toast.makeText(getApplicationContext(), "" + updatedTime + "haha",
					Toast.LENGTH_LONG).show();

		}
	}
	
	//calculate distance between 2 point
	private float calculateDistance(GeoPoint startPoint, GeoPoint endPoint){
		float[] result = new float[1];
		Location.distanceBetween(startPoint.getLatitude(), startPoint.getLongitude(), 
				endPoint.getLatitude(), endPoint.getLongitude(), result);
		return result[0];
	}

	// set your map and enable default zoom controls
	private void setupMapView() {
		this.map = (MapView) findViewById(R.id.map);

		map.setBuiltInZoomControls(true);
	}

	// set up a MyLocationOverlay and execute the runnable once we have a
	// location fix
	private void setupMyLocation() {
		this.myLocationOverlay = new MyLocationOverlay(this, map);
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.runOnFirstFix(new Runnable() {
			@Override
			public void run() {
				GeoPoint currentLocation = myLocationOverlay.getMyLocation();
				map.getController().animateTo(currentLocation);
				map.getController().setZoom(18);
				map.getOverlays().add(myLocationOverlay);
				myLocationOverlay.setFollowing(true);
			}
		});
	}

	
	
	

	// create a route and display on the map
	private void displayRoute() {
		RouteManager routeManager = new RouteManager(this);
		routeManager.setMapView(map);
		routeManager.createRoute("San Francisco, CA", "Fremont, CA");
	}

	// enable features of the overlay
	@Override
	protected void onResume() {
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
		super.onResume();
	}

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();
	}

	

	private void drawTrackLine() {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLUE);
		paint.setAlpha(100);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeWidth(5);
		LineOverlay line = new LineOverlay(paint);
		line.setData(trackedPoint);

		map.getOverlays().add(line);
		// this.map.getController().z
		map.invalidate();
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
			if (secs % 10 == 0) {
				GeoPoint currentLocation = myLocationOverlay.getMyLocation();
				trackedPoint.add(currentLocation);
				// Toast.makeText(TrainingActivity.this,
				// currentLocation.toString(), Toast.LENGTH_SHORT).show();
				drawTrackLine();
			}
		}
	};

	/*
	 * public void onBackPressed() { super.onBackPressed();
	 * overridePendingTransition(0, 0); }
	 */
	// return false since no route is being displayed
	@Override
	public boolean isRouteDisplayed() {
		return false;
	}

}
