package project.trackfit.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import project.trackfit.R;
import project.trackfit.controller.DashboardController;
import project.trackfit.model.History;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuDashDist extends Activity implements OnClickListener {
	
	private Button home;
	private Button tracker;
	private Button history;
	private Button about;
	private Button profile;
	private Button calorie;
	private Button speed;
	private Button time;
	private Context context;
	private DashboardController dashboardController;
	private String cDate;

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dash_dist);
		overridePendingTransition(0,0);
		context = this;
		
		DateFormat format  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		cDate = format.format(currentDate);
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		calorie = (Button) findViewById(R.id.buttonCalories);
		speed = (Button) findViewById(R.id.buttonSpeed);
		time = (Button) findViewById(R.id.buttonTime);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		calorie.setOnClickListener(this);
		speed.setOnClickListener(this);
		time.setOnClickListener(this);
		
		showDistanceGraph();
	}

	private void showDistanceGraph() {
		// TODO Auto-generated method stub
		dashboardController = new DashboardController(context);
		final ArrayList<History> historyList = dashboardController.getHistoryList();
		
		GraphViewData[] dataSeries = new GraphViewData[historyList.size()];
		String[] labelSeries = new String[] {"earlier", "recently"};
		
		for (int i = 0; i < dataSeries.length; i++) {
			History temp = historyList.get(i);
			double val = temp.getDistance();
			dataSeries[i] = new GraphViewData(i + 1, val);
			System.out.println("val " + i + " = " + val);
		}
		
		GraphViewSeries exampleSeries = new GraphViewSeries(dataSeries);  
		  
		GraphView graphView = new LineGraphView(  
		      this // context  
		      , "Distance Graphic" // heading  
		);  
		/*graphView.setCustomLabelFormatter(new CustomLabelFormatter() {

			@Override
			public String formatLabel(double value, boolean isValueX) {
				// TODO Auto-generated method stub
				if (isValueX) {
					System.out.println("value x = " + value);
					int index = (int) value;
					History temp = historyList.get(index - 1);
					int year = temp.getYear();
					int month = temp.getMonth();
					int day = temp.getDay();
					int cDay = Integer.parseInt(cDate.substring(8,10));
					int cMonth = Integer.parseInt(cDate.substring(5,7));
					int cYear = Integer.parseInt(cDate.substring(0,4));
					
					int diff = 0;
					if (year < cYear) {
						diff = cYear - year;
						if (diff == 1) {
							return "last year";
						} else return diff + " years ago";
					} else if (year == cYear) {
						if (month < cMonth) {
							diff = cMonth - month;
							if (diff == 1) {
								return "last month";
							} else return diff + "months ago";
						} else if (month == cMonth) {
							if (day < cDay) {
								diff = cDay - day;
								if (diff == 1) {
									return "yesterday";
								} else return diff + "days ago";
							}
						}
					}
					 
				}
				return null;
			}
			
		});*/
		graphView.addSeries(exampleSeries); // data
		graphView.setScrollable(true);
		graphView.setScalable(true);
		graphView.setViewPort(1, 5);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.setHorizontalLabels(labelSeries);
		graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.small));
		graphView.getGraphViewStyle().setNumVerticalLabels(10);
		graphView.getGraphViewStyle().setNumHorizontalLabels(5);
		  
		LinearLayout layout = (LinearLayout) findViewById(R.id.contentDistance);  
		layout.addView(graphView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
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
		} else if (v.equals(calorie)) {
			startActivity(new Intent(getApplicationContext(), MenuDashCal.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
