package project.trackfit.view;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import project.trackfit.R;
import project.trackfit.controller.DashboardController;
import project.trackfit.model.History;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuDashCal extends Activity implements OnClickListener {
	
	Button home;
	Button tracker;
	Button history;
	Button about;
	Button profile;
	Button distance;
	Button speed;
	Button time;
	Context context;
	DashboardController dashboardController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_cal);
		overridePendingTransition(0,0);
		context = this;
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		distance = (Button) findViewById(R.id.buttonDistance);
		speed = (Button) findViewById(R.id.buttonSpeed);
		time = (Button) findViewById(R.id.buttonTime);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		distance.setOnClickListener(this);
		speed.setOnClickListener(this);
		time.setOnClickListener(this);
		
		showCalorieGraph();
	}
	
	private void showCalorieGraph() {
		// TODO Auto-generated method stub
		dashboardController = new DashboardController(context);
		ArrayList<History> historyList = dashboardController.getHistoryList();
		
		GraphViewData[] dataSeries = new GraphViewData[historyList.size()];
		String[] labelSeries = new String[historyList.size()]; 
		
		for (int i = 0; i < dataSeries.length; i++) {
			History temp = historyList.get(i);
			double val = temp.getCalorie();
			String label = temp.getDay() + "/" + temp.getMonth();
			dataSeries[i] = new GraphViewData(i + 1, val);
			labelSeries[i] = label;
			System.out.println("val = " + val);
		}
		
		GraphViewSeries exampleSeries = new GraphViewSeries(dataSeries);  
			  
			GraphView graphView = new LineGraphView(  
			      this // context  
			      , "Calorie Graphics" // heading  
			);  
			graphView.addSeries(exampleSeries); // data
			graphView.setScrollable(true);
			graphView.setScalable(true);
			graphView.setViewPort(1, 10);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			//graphView.setHorizontalLabels(labelSeries);
			graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.small));
			graphView.getGraphViewStyle().setNumHorizontalLabels(10);
			graphView.getGraphViewStyle().setNumHorizontalLabels(5);
			  
			LinearLayout layout = (LinearLayout) findViewById(R.id.contentCalorie);  
			layout.addView(graphView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dash_cal, menu);
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
		} else if (v.equals(speed)) {
			startActivity(new Intent(getApplicationContext(), MenuDashSpeed.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(time)) {
			startActivity(new Intent(getApplicationContext(), MenuDashTime.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		}
	}
	
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
}
