package project.trackfit.view;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import project.trackfit.R;
import project.trackfit.controller.RemoteControlReceiver;
import project.trackfit.controller.SoundController;
import project.trackfit.controller.SportTrackController;
import android.location.Location;
import android.location.LocationListener;
import android.location.Criteria;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

@SuppressLint("DefaultLocale")
public class MenuSportTrack extends Activity implements LocationListener, 
		SensorEventListener, OnClickListener {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	RemoteControlReceiver r;
	Set<String> setStart;
	Set<String> setPause;
	Set<String> setResume;
	Set<String> setStop;

	SensorManager sensorManager;
	Sensor accelerometer;
	boolean mInitialized;
	private float mLastX, mLastY, mLastZ;
	private float[] gravity;
	private float[] linear_acceleration;
	private final float NOISE = (float) 2.0;

	SportTrackController stc;
	LocationManager locationManager;
	SoundController sc;

	List<LatLng> routePoints;
	protected GoogleMap map;

	boolean isStart;

	float distance;
	float distanceTo;

	int invisible = View.GONE;
	int visible = View.VISIBLE;

	//private View frame;
	private TextView TVstopwatch;
	private TextView TVAvgSpeed;
	private TextView TVDistance;
	private Button home;
	private Button history;
	private Button dashboard;
	private Button about;
	private Button profile;
	private Button start;
	private Button pause;
	private Button resume;
	private Button stop;

	private long timeInMilliseconds = 0L;
	private long startTime = 0L;
	private long updatedTime = 0L;
	private long timeSwapBuff = 0L;
	
	Location lokasLama;
	Location initialLocation;
	Location lastLocation;
	Location finalLocation;
	Location startPos;
	Location endPos;

	float totalDistance;
	float jarakSampeSekarang;
	float jarakAwalAkhir;
	float avgSpeed;

	int minutes;
	int seconds;
	int hour;

	int point;
	int counter = 0;
	
	String selectedAct;
	String shareMessage;
	Context context;
	Bitmap peta;
	String time = "";
	
	Handler timerHandler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport_track);
		mInitialized = false;
		context = this;
		
		Intent intent = getIntent();
		selectedAct = intent.getStringExtra("activity");
		System.out.println("selectedAct = " +  selectedAct);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		
		stc = new SportTrackController(this);
		sc = new SoundController(this);


		this.gravity = new float[3];
		this.linear_acceleration = new float[3];

		this.gravity[0] = 0;
		this.gravity[1] = 0;
		this.gravity[2] = 0;
		stc = new SportTrackController(this);

		// Media Button Event Listener
		((AudioManager) getSystemService(AUDIO_SERVICE))
				.registerMediaButtonEventReceiver(new ComponentName(this,
						RemoteControlReceiver.class));
		IntentFilter filter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);// "android.intent.action.MEDIA_BUTTON"
		r = new RemoteControlReceiver(MenuSportTrack.this);
		filter.setPriority(10000);
		registerReceiver(r, filter);
		registerReceiver(r, new IntentFilter(Intent.ACTION_HEADSET_PLUG));

		setStart = new HashSet<String>();
		setPause = new HashSet<String>();
		setResume = new HashSet<String>();
		setStop = new HashSet<String>();

		setStart.add("start");
		setStart.add("star");
		setStart.add("tart");
		setStart.add("fuck");
		setStart.add("f***");
		setStart.add("stock");
		setPause.add("pause");
		setPause.add("both");
		setPause.add("bose");
		setPause.add("police");
		setPause.add("polish");
		setPause.add("boobs");
		setPause.add("post");
		setPause.add("boz");
		setPause.add("posts");
		setPause.add("pose");
		setPause.add("old");
		setPause.add("bowls");
		setResume.add("resume");
		setResume.add("the food");
		setResume.add("the sup");
		setResume.add("assume");
		setResume.add("the zoom");
		setResume.add("the soon");
		setResume.add("this room");
		setStop.add("stop");
		setStop.add("sup");
		setStop.add("help");
		checkVoiceRecognition();

		routePoints = new ArrayList<LatLng>();
		setupView();
		setupEvent();
		minutes = 0;

		try {
			Log.d("test", "masuk try cacth");

			initializeMap();
			isStart = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setupView() {
		//frame = findViewById(R.id.frame);
		TVstopwatch = (TextView) findViewById(R.id.textViewDuration);
		TVAvgSpeed = (TextView) findViewById(R.id.textViewAvgSpeed);
		TVDistance = (TextView) findViewById(R.id.textViewDistance);
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
		if (v.equals(home)) {
			if (!isStart){
				startActivity(new Intent(getApplicationContext(), MenuHome.class)
						.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
								| Intent.FLAG_ACTIVITY_CLEAR_TOP));
				unregisterReceiver(r);
				finish();
			}
		} else if (v.equals(about)) {
			if (!isStart){
				startActivity(new Intent(getApplicationContext(), MenuAbout.class)
						.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				unregisterReceiver(r);
				finish();
			}
		} else if (v.equals(history)) {
			if (!isStart){
				startActivity(new Intent(getApplicationContext(),
						MenuActHistory.class)
						.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				unregisterReceiver(r);
				finish();
			}
		} else if (v.equals(dashboard)) {
			if (!isStart){
				startActivity(new Intent(getApplicationContext(),
						MenuDashDist.class)
						.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				unregisterReceiver(r);
				finish();
			}
		} else if (v.equals(profile)) {
			if(!isStart){
				startActivity(new Intent(getApplicationContext(), MenuProfile.class)
						.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				unregisterReceiver(r);
				finish();
			}
		} else if (v.equals(start)) {
			startRun();
		} else if (v.equals(pause)) {
			pauseRun();
		} else if (v.equals(resume)) {
			resumeRun();
		} else if (v.equals(stop)) {
			captureMapScreen();
			stopRun();
			setVaribleToDefault();
		}
	}

	private void stopRun() {
		isStart = false;
		start.setVisibility(visible);
		pause.setVisibility(invisible);
		resume.setVisibility(invisible);
		stop.setVisibility(invisible);
		timeSwapBuff = 0;
		timerHandler.removeCallbacks(timerRunnable);
		//snapshot(GoogleMap.SnapshotReadyCallback callback);
		stopTrain();
		trainingSummary();
	}

	private void resumeRun() {
		isStart = true;
		start.setVisibility(invisible);
		pause.setVisibility(visible);
		resume.setVisibility(invisible);
		stop.setVisibility(visible);
		startTime = SystemClock.uptimeMillis();
		timerHandler.postDelayed(timerRunnable, 0);
	}

	private void pauseRun() {
		isStart = false;
		start.setVisibility(invisible);
		pause.setVisibility(invisible);
		resume.setVisibility(visible);
		stop.setVisibility(invisible);
		timeSwapBuff += timeInMilliseconds;
		timerHandler.removeCallbacks(timerRunnable);
	}

	private void startRun() {
		if (readyToRun()) {

			isStart = true;
			start.setVisibility(invisible);
			pause.setVisibility(visible);
			resume.setVisibility(invisible);
			stop.setVisibility(visible);
			startTime = SystemClock.uptimeMillis();
			timerHandler.postDelayed(timerRunnable, 0);

		}
	}

	private void setVaribleToDefault() {
		avgSpeed = 0f;
		point = 0;
		jarakSampeSekarang = 0f;
		jarakAwalAkhir = 0f;
		minutes = 0;
		distance = 0f;
		distanceTo = 0f;
		lokasLama = null;
		initialLocation = null;
		lastLocation = null;
		finalLocation = null;
		totalDistance = 0f;
		startPos = null;
		endPos = null;
		counter = 0;

	}

	private float calculateDistance(Location startLoc, Location endLoc) {
		distanceTo = endLoc.distanceTo(startLoc);
		float[] result = new float[1];
		Location.distanceBetween(startLoc.getLatitude(),
				startLoc.getLongitude(), endLoc.getLatitude(),
				endLoc.getLongitude(), result);
		return result[0];
	}

	private float calculateAvgSpeed(float speed) {
		float avg = ((avgSpeed * (point - 1)) + speed) / point;
		return avg;
	}

	private double calculateCalories(String act, int age, int weight, float timeMinute) {
		int heartRateWalking = 100;
		int heartRateRunning = 120;
		int heartRateCycling= 110;
		double cal = 0.0;
		if (act.equals("Walking"))
		{
			cal = ((age * 0.2017) + (weight * 0.09036) + (heartRateWalking * 0.6309) - 55.0969)
					* timeMinute / 5.184;
		}else if (act.equals("Running")){
			cal = ((age * 0.2017) + (weight * 0.09036) + (heartRateRunning * 0.6309) - 55.0969)
					* timeMinute / 3.184;
		}else if (act.equals("Cycling")){
			cal = ((age * 0.2017) + (weight * 0.09036) + (heartRateCycling * 0.6309) - 55.0969)
					* timeMinute / 4.184;
		}
		
		return cal;
	}

	// enable features of the overlay
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		initializeMap();
	}

	// disable features of the overlay when in the background
	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
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
			TVstopwatch.setText("" + hours + ":" + String.format("%02d", mins)
					+ ":" + String.format("%02d", secs));
			hour = hours;
			minutes = mins;
			seconds = secs;

			timerHandler.postDelayed(this, 0);

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

			// Log.d("test",
			// "initial map "+currentLocation.getLatitude()+":"+currentLocation.getLongitude());

			CameraPosition camPos = new CameraPosition.Builder()
					.target(new LatLng(currentLocation.getLatitude(),
							currentLocation.getLongitude())).zoom(15.8f)
					.build();
			CameraUpdate camUpdate = CameraUpdateFactory
					.newCameraPosition(camPos);

			map.moveCamera(camUpdate);

			//MarkerOptions marker = new MarkerOptions().position(
			//		new LatLng(currentLocation.getLatitude(), currentLocation
			//				.getLongitude())).title("You're here");

			//map.addMarker(marker);

			// startPos = new Location(currentLocation);
			// Log.d("test",
			// "start="+startPos.getLatitude()+":"+startPos.getLongitude());

			initialLocation = currentLocation;
			lastLocation = currentLocation;
			finalLocation = currentLocation;
			Log.d("test", "Initiating Maps");
			if (map == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	private boolean readyToRun() {

		boolean isNetworkOK = isNetworkEnabled();
		boolean isGPSOK = isGPSEnabled();
		jarakAwalAkhir = 0f;
		jarakSampeSekarang = 0f;
		avgSpeed = 0f;
		point = 0;

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {

			if (isNetworkOK && isGPSOK) {
				if (isNetworkOK) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 1000, 1, this);
				}

				if (isGPSOK) {
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 1000, 1, this);
				}

				isStart = true;
				// makeMarker(initialLocation, "START");
				return true;
			} else {
				Toast.makeText(getApplicationContext(), "Check your network and GPS",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		} else {
			Toast.makeText(getApplicationContext(), "Check your network and GPS",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	} 
		

	private void stopTrain() {
		Criteria crit = new Criteria();
		finalLocation = locationManager.getLastKnownLocation(locationManager
				.getBestProvider(crit, false));
		if (initialLocation != null && finalLocation != null) {
			totalDistance = calculateDistance(initialLocation, finalLocation);
			// makeMarker(finalLocation, "FINISH");
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void trainingSummary() {


		boolean success = false;
		jarakAwalAkhir = jarakSampeSekarang;
		float calorie;
		if (minutes!=0){
			calorie = (float) calculateCalories(selectedAct, stc.getAge(),
					stc.getWeight(), minutes);
		}else{
			float menit = (float) 1/seconds;
			calorie = (float) calculateCalories(selectedAct, stc.getAge(),
					stc.getWeight(), menit);
		}
		
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		String tanggal = format.format(currentDate);

		int day = Integer.parseInt(tanggal.substring(8, 10));
		int month = Integer.parseInt(tanggal.substring(5, 7));
		int year = Integer.parseInt(tanggal.substring(0, 4));
		
		if (selectedAct.equals("Walking")) {
			success = stc.addHistory(1, 1, totalDistance, hour, minutes,
					seconds, calorie, avgSpeed, day, month, year);
		} else if (selectedAct.equals("Running")) {
			success = stc.addHistory(1, 2, totalDistance, hour, minutes,
					seconds, calorie, avgSpeed, day, month, year);
		} else if (selectedAct.equals("Cycling")) {
			success = stc.addHistory(1, 3, totalDistance, hour, minutes,
					seconds, calorie, avgSpeed, day, month, year);
		}
		
		
		if (success) {
			Toast.makeText(getApplicationContext(), "History saved!", Toast.LENGTH_LONG).show();
			if (hour < 10) {
				time.concat("0" + hour);
			}
			if (minutes < 10) {
				time.concat("0" + minutes);
			}
			if (seconds < 10) {
				time.concat("0" + seconds);
			}
			time = hour + ":" + minutes + ":" + seconds;
			
			String distance = String.format("%.0f", totalDistance);
			String cal = String.format("%.0f", calorie);
			shareMessage = "I was out "+ selectedAct +" for " + distance + " m in " + time + ". I burnt " + cal + " cal! #TrackFit";
			Intent intent = new Intent(context, SharePopUp.class);
			intent.putExtra("message", shareMessage);
			startActivity(intent);
		}
		else
			Toast.makeText(getApplicationContext(), "History cannot be saved", Toast.LENGTH_LONG)
					.show();
	}
	
	private boolean isGPSEnabled() {
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	private boolean isNetworkEnabled() {
		return locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	}

	// jalan tiap kali gerak/pindah posisi
	@Override
	public void onLocationChanged(Location loc) {
		if (isStart) {
			try {
				/** Algoritma alternatif */
				LatLng currentLocation = new LatLng(map.getMyLocation()
						.getLatitude(), map.getMyLocation().getLongitude());
				routePoints.add(currentLocation);
				Polyline route = map.addPolyline(new PolylineOptions()
						.geodesic(true));
				route.setPoints(routePoints);
				
				Location loca = new Location("");
				loca.setLatitude(currentLocation.latitude);
				loca.setLongitude(currentLocation.longitude);
				
				/** Algoritma yang seharusnya */
				/*
				 * LatLng currentLatLng = new LatLng(loc.getLatitude(),
				 * loc.getLongitude()); routePoints.add(currentLatLng); Polyline
				 * route = map.addPolyline(new PolylineOptions() .geodesic(true));
				 * route.setPoints(routePoints);
				 */
				if (startPos == null) {
					startPos = loca;
				} else {
					jarakSampeSekarang += calculateDistance(startPos, loca);
					startPos = loca;
				}
				TVDistance.setText(String.format("%.0f",jarakSampeSekarang));
				Log.d("test",
						"berubah" + loc.getLatitude() + ":" + loc.getLongitude());
				point++;
				avgSpeed = calculateAvgSpeed(loc.getSpeed());
			} catch (NullPointerException e){
				Toast.makeText(this, "GPS not ready yet",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == RESULT_OK) {

				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					// If first Match contains the 'search' word
					// Then start web search.
					if (textMatchList.get(0).contains("search")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("search", "");
						Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
						search.putExtra(SearchManager.QUERY, searchQuery);
						startActivity(search);
					} else {
						// populate the Matches
						/*
						 * mlvTextMatches.setAdapter(new ArrayAdapter<String>(
						 * this, android.R.layout.simple_list_item_1,
						 * textMatchList));
						 */
						Iterator<String> iterator = textMatchList.iterator();
						while (iterator.hasNext()) {
							String voice = iterator.next();
							if (setStop.contains(voice)) {
								if(isStart){
									sc.PlaySound("STOP");
									stopRun();
								}else{
									sc.PlaySound("WRONGCOMMAND");
								}
							} else if (setStart.contains(voice)) {
								if(isStart){
									sc.PlaySound("START");
									startRun();
								}else{
									sc.PlaySound("WRONGCOMMAND");
								}
							} else if (setResume.contains(voice)) {
								if(isStart){
									sc.PlaySound("RESUME");
									resumeRun();
								}else{
									sc.PlaySound("WRONGCOMMAND");
								}
							} else if (setPause.contains(voice)) {
								if(isStart){
									sc.PlaySound("PAUSE");
									pauseRun();
								}else{
									sc.PlaySound("WRONGCOMMAND");
								}
							} else{
								sc.PlaySound("NOTCLEAR");
							}
								
						}
					}

				}
				// Result code for various error.
			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

	void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	public void speak() {
		System.out.println("kepanggil kok speak nya");
		Log.d("debug", "speak");
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());

		// Display an hint to the user about what he should say.
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a command");

		// Given an hint to the recognizer about what the user is going to say
		// There are two form of language model available
		// 1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		// 2.LANGUAGE_MODEL_FREE_FORM : If not sure about the words or phrases
		// and its domain.
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
		/*
		 * // If number of Matches is not selected then return show toast
		 * message if (msTextMatches.getSelectedItemPosition() ==
		 * AdapterView.INVALID_POSITION) { Toast.makeText(this,
		 * "Please select No. of Matches from spinner",
		 * Toast.LENGTH_SHORT).show(); return; }
		 * 
		 * int noOfMatches = Integer.parseInt(msTextMatches
		 * .getSelectedItem().toString()); // Specify how many results you want
		 * to receive. The results will be // sorted where the first result is
		 * the one with higher confidence.
		 * intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		 */
		// Start the Voice recognizer activity for the result.
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (!mInitialized) {
			mLastX = x;
			mLastY = y;
			mLastZ = z;
			mInitialized = true;
		} else {
			float deltaX = Math.abs(mLastX - x);
			float deltaY = Math.abs(mLastY - y);
			float deltaZ = Math.abs(mLastZ - z);

			if (deltaX < NOISE)
				deltaX = (float) 0.0;
			if (deltaY < NOISE)
				deltaY = (float) 0.0;
			if (deltaZ < NOISE)
				deltaZ = (float) 0.0;

			mLastX = x;
			mLastY = y;
			mLastZ = z;
			float kFilteringFactor = 0.6f;

			gravity[0] = (x * kFilteringFactor)
					+ (gravity[0] * (1.0f - kFilteringFactor));
			gravity[1] = (y * kFilteringFactor)
					+ (gravity[1] * (1.0f - kFilteringFactor));
			gravity[2] = (z * kFilteringFactor)
					+ (gravity[2] * (1.0f - kFilteringFactor));

			linear_acceleration[0] = (x - gravity[0]);
			linear_acceleration[1] = (y - gravity[1]);
			linear_acceleration[2] = (z - gravity[2]);

			float magnitude = 0.0f;
			magnitude = (float) Math.sqrt(linear_acceleration[0]
					* linear_acceleration[0] + linear_acceleration[1]
					* linear_acceleration[1] + linear_acceleration[2]
					* linear_acceleration[2]);
			magnitude = Math.abs(magnitude);
			// if (magnitude >1) {
			// this.counter++;
			if (isStart) {
				TVAvgSpeed.setText(String.format("%.0f", magnitude));
				// if (counter == 5) {
				// int pointNow = Integer.parseInt(point.getText().toString());
				// point.setText("" + (pointNow + 1));
				// counter = 0;
				// }
			}
		}
	}
	
	/*
	private void MakeMapsBaloon(LatLng position){
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(position.latitude, position
						.longitude)).title("You're here");

		map.addMarker(marker);
	}*/
	
	/*public void captureMap() {
		System.out.println("captureMap kepanggil");
		SnapshotReadyCallback callback = new SnapshotReadyCallback() {
	        @Override
	        public void onSnapshotReady(Bitmap snapshot) {
	        	System.out.println("on snapshot ready");
	            peta = snapshot;
	            Log.d("debug", peta.getHeight()+":"+peta.getWidth()+":"+":"+peta.describeContents());
	            System.out.println("bitmap = " + peta.describeContents());
	            try {
	                   FileOutputStream out = new FileOutputStream("/" + Environment.getExternalStorageDirectory().getPath() + "/"+"trackFit2"+".png");
	                   peta.compress(Bitmap.CompressFormat.PNG, 100, out);
	            } catch (Exception e) {
	                   e.printStackTrace();
	            }
	        }
	    };
	    map.snapshot(callback);
	}*/
	
	public void captureMapScreen() {
        SnapshotReadyCallback callback = new SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
            	peta = snapshot;
            	System.out.println("peta = " + snapshot);
    			try {
	                   FileOutputStream out = new FileOutputStream("/" + Environment.getExternalStorageDirectory().getPath() + "/"+"trackFit2"+".png");
	                   peta.compress(Bitmap.CompressFormat.PNG, 100, out);
	            } catch (Exception e) {
	                   e.printStackTrace();
	            }
            }
        };

        map.snapshot(callback);
        //System.out.println("peta = " + peta);

    }
	 

	@Override
	public void onProviderDisabled(String arg0) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

    //map.snapshot(callback);
}
