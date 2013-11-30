package project.trackfit.view;

import java.util.ArrayList;
import java.util.List;

import project.trackfit.R;
import android.location.Location;
import android.location.LocationListener;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MenuSportTrack extends Activity implements LocationListener,
		OnClickListener {
	List<LatLng> routePoints;
	protected GoogleMap map;

	LocationManager locationManager;
	boolean isStart;
	float avgSpeed;
	int point;
	float distance;

	int invisible = View.GONE;
	int visible = View.VISIBLE;

	private TextView TVstopwatch;
	private TextView TVDistance;
	private TextView TVAvgSpeed;
	private Button home;
	private Button history;
	private Button dashboard;
	private Button about;
	private Button profile;
	private Button start;
	private Button pause;
	private Button resume;
	private Button stop;

	Location initialLocation;
	Location lastLocation;
	Location finalLocation;
	float distanceTo;
	
	private long timeInMilliseconds = 0L;
	private long startTime = 0L;
	private long updatedTime = 0L;
	private long timeSwapBuff = 0L;
	private long time = 0L;

	float totalDistance;
	Location startPos, endPos;

	Handler timerHandler = new Handler();
	int counter = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport_track);

		routePoints = new ArrayList<LatLng>();
		setupView();
		setupEvent();

		try {
			Log.d("test", "masuk try cacth");
			
			initializeMap();
			isStart = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setupView() {
		TVstopwatch = (TextView) findViewById(R.id.textViewDuration);
		TVDistance = (TextView) findViewById(R.id.textViewDistance);
		TVAvgSpeed = (TextView) findViewById(R.id.textViewAvgSpeed);
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

	private void setupEvent() {
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
			if(prepareToRun()){
				//ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				//NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				//if (networkInfo != null && networkInfo.isConnected() && isGPSEnabled() && isNetworkEnabled()){
					isStart=true;
					start.setVisibility(invisible);
					pause.setVisibility(visible);
					resume.setVisibility(invisible);
					stop.setVisibility(visible);
					startTime = SystemClock.uptimeMillis();
					timerHandler.postDelayed(timerRunnable, 0);
				//}
			}
		} else if (v.equals(pause)) {
			isStart = false;
			start.setVisibility(invisible);
			pause.setVisibility(invisible);
			resume.setVisibility(visible);
			stop.setVisibility(invisible);
			timeSwapBuff += timeInMilliseconds;
			timerHandler.removeCallbacks(timerRunnable);
		} else if (v.equals(resume)) {
			isStart = true;
			start.setVisibility(invisible);
			pause.setVisibility(visible);
			resume.setVisibility(invisible);
			stop.setVisibility(visible);
			startTime = SystemClock.uptimeMillis();
			timerHandler.postDelayed(timerRunnable, 0);
		} else if (v.equals(stop)) {
			isStart = false;
			start.setVisibility(visible);
			pause.setVisibility(invisible);
			resume.setVisibility(invisible);
			stop.setVisibility(invisible);
			timeSwapBuff = 0;
			timerHandler.removeCallbacks(timerRunnable);
			stopTrain();
			trainingSummary();

			//Toast.makeText(getApplicationContext(), "" + updatedTime + "haha",
			//		Toast.LENGTH_LONG).show();
		}
	}

	// calculate distance between 2 point
	private float calculateDistance(LatLng startPoint, LatLng endPoint) {
		
		float[] result = new float[1];
		Location.distanceBetween(startPoint.latitude, startPoint.longitude,
				endPoint.latitude, endPoint.longitude, result);
		return result[0];
	}
	
	

	private float calculateDistance(Location startLoc, Location endLoc) {
		distanceTo = endLoc.distanceTo(startLoc);
		float[] result = new float[1];
		Location.distanceBetween(startLoc.getLatitude(),
				startLoc.getLongitude(), endLoc.getLatitude(),
				endLoc.getLongitude(), result);
		return result[0];
	}

	// enable features of the overlay
	@Override
	protected void onResume() {
		super.onResume();
		//initializeMap();
	}

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
	}

	private Runnable timerRunnable = new Runnable() {
		public void run() {

			// run a timer
			timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
			updatedTime = timeSwapBuff + timeInMilliseconds;
			int secs = (int) (updatedTime / 1000);
			int mins = secs / 60;
			int hours = mins / 60;
			secs = secs % 60;
			int milliSecs = (int) (updatedTime % 1000);
			TVstopwatch.setText("" + hours + ":" + String.format("%02d", mins)
					+ ":" + String.format("%02d", secs) + ":"
					+ String.format("%02d", milliSecs));
			timerHandler.postDelayed(this, 0);

			// draw tracking lines
			/*
			 * if (secs % 3 == 0) { LatLng currentPoint = new
			 * LatLng(map.getMyLocation().getLatitude(),
			 * map.getMyLocation().getLongitude());
			 * 
			 * 
			 * 
			 * }
			 */
			//if (secs % 10 == 0) {
			//	LatLng currentLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
			//	routePoints.add(currentLocation);
	
			//	Polyline route = map.addPolyline(new PolylineOptions().geodesic(true));
			//	route.setPoints(routePoints);
			//}
		}
	};

	private void initializeMap() {

		if (map == null) {
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			map.setMyLocationEnabled(true);
		
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			Location currentLocation = locationManager
					.getLastKnownLocation(locationManager.getBestProvider(
							criteria, false));
			
			//Log.d("test", "haha");
			//Log.d("test", currentLocation.getLatitude()+":"+currentLocation.getLongitude());
			
			CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(15.8f).build();
			CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
			
			map.moveCamera(camUpdate);

			MarkerOptions marker = new MarkerOptions()
					.position(
							new LatLng(currentLocation.getLatitude(),
									currentLocation.getLongitude()))
					.title("You're here");

			map.addMarker(marker);
			
			//startPos = new Location(currentLocation);
			//Log.d("test", "start="+startPos.getLatitude()+":"+startPos.getLongitude());
			
			initialLocation = currentLocation;
			lastLocation = currentLocation;
			finalLocation = currentLocation;
			if (map == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
	private boolean prepareToRun() {

		
		boolean isNetworkOK = isNetworkEnabled();
		boolean isGPSOK = isGPSEnabled();
		avgSpeed = 0f;
		point = 0;
		
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnected()) {
		
			if(isNetworkOK && isGPSOK) {
				
				
				if(isGPSOK) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
				}
				if(isNetworkOK) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, this);
				}
				isStart = true;
				//makeMarker(initialLocation, "START");
				return true;
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	private void stopTrain() {
		//Criteria crit = new Criteria();
		//finalLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));
		//totalDistance = calculateDistance(initialLocation, finalLocation);
		Criteria crit = new Criteria();
		finalLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(crit, false));
		if(initialLocation != null && finalLocation != null) {
			totalDistance = calculateDistance(initialLocation, finalLocation);
			//makeMarker(finalLocation, "FINISH");
		}
	}

	private float calculateAvgSpeed(Location loc) {

		float s = loc.getSpeed();

		float avg = ((avgSpeed * (point - 1)) + s) / point;
		return avg;
	}
	

	
	
	private void trainingSummary() {
		Toast.makeText(getApplicationContext(),"speed: " + avgSpeed + " distance: " + totalDistance +" Time: "+ updatedTime, Toast.LENGTH_LONG)
				.show();
	}

	private boolean isGPSEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	private boolean isNetworkEnabled() {
		return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	@Override
	public void onLocationChanged(Location loc) {
		if (isStart) {
			
			LatLng currentLocation = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
			routePoints.add(currentLocation);
	
			Polyline route = map.addPolyline(new PolylineOptions().geodesic(true));
			route.setPoints(routePoints);
			//LatLng currentLatLng = new LatLng(loc.getLatitude(),
			//		loc.getLatitude());
			//routePoints.add(currentLatLng);
			//Polyline route = map.addPolyline(new PolylineOptions()
			//		.geodesic(true));
			//route.setPoints(routePoints);
			
			Log.d("test", "berubah"+loc.getLatitude()+":"+loc.getLongitude());
			
			Toast.makeText(getApplicationContext(),"masuk" + counter++, Toast.LENGTH_SHORT)
			.show();
			
			point++;
			calculateAvgSpeed(loc);
			
			
			
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
