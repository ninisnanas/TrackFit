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
import com.mapquest.android.maps.MyLocationOverlay;
import com.mapquest.android.maps.RouteManager;

public class MenuSportTrack extends MapActivity implements OnClickListener {
	protected MapView map;
    private MyLocationOverlay myLocationOverlay;
	
	Button home;
	Button history;
	Button dashboard;
	Button about;
	Button profile;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sport_track);
		overridePendingTransition(0,0);
	    setupMapView();
	    setupMyLocation();
	    displayRoute();
	    
	    home = (Button) findViewById(R.id.HomeIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		
		home.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);

		// set the zoom level, center point and enable the default zoom controls 
		MapView map = (MapView) findViewById(R.id.map);
		map.getController().setZoom(9);
		map.getController().setCenter(new GeoPoint(38.892155,-77.036195));
		map.setBuiltInZoomControls(true);
		
    }
    
    
    // set your map and enable default zoom controls
    private void setupMapView() {
        this.map = (MapView) findViewById(R.id.map);
        map.setBuiltInZoomControls(true);
    }
    
    // set up a MyLocationOverlay and execute the runnable once we have a location fix 
    private void setupMyLocation() {
        this.myLocationOverlay = new MyLocationOverlay(this, map);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.runOnFirstFix(new Runnable() {
          @Override
          public void run() {
            GeoPoint currentLocation = myLocationOverlay.getMyLocation();
            map.getController().animateTo(currentLocation);
            map.getController().setZoom(14);
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
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(home)) {
			startActivity(new Intent(getApplicationContext(), MenuHome.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION|Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
		} else if (v.equals(about)) {
			startActivity(new Intent(getApplicationContext(), MenuAbout.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(history)) {
			startActivity(new Intent(getApplicationContext(), MenuActHistory.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(dashboard)) {
			startActivity(new Intent(getApplicationContext(), MenuDashDist.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(profile)) {
			startActivity(new Intent(getApplicationContext(), MenuProfile.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
