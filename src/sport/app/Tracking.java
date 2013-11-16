package sport.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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

public class Tracking extends MapActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_tracking);

      // set the zoom level, center point and enable the default zoom controls 
      MapView map = (MapView) findViewById(R.id.map);
      map.getController().setZoom(9);
      map.getController().setCenter(new GeoPoint(38.892155,-77.036195));
      map.setBuiltInZoomControls(true);
    }

    // return false since no route is being displayed 
    @Override
    public boolean isRouteDisplayed() {
      return false;
    }
  }
