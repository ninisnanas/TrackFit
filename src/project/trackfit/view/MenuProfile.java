package project.trackfit.view;

import project.trackfit.R;
import project.trackfit.controller.ProfileController;
import project.trackfit.model.User;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuProfile extends Activity implements OnClickListener {
	Button home;
	Button tracker;
	Button history;
	Button dashboard;
	Button about;
	Button profile;
	Button confirm;
	EditText name;
	EditText age;
	EditText weight;
	EditText height;
	
	boolean newUser;
	
	Context context;
	ProfileController profileController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		overridePendingTransition(0,0);
		
		context = this;
		profileController = new ProfileController(context);
		
		home = (Button) findViewById(R.id.HomeIconButton);
		tracker = (Button) findViewById(R.id.TrackerIconButton);
		history = (Button) findViewById(R.id.HistoryIconButton);
		dashboard = (Button) findViewById(R.id.DashboardIconButton);
		about = (Button) findViewById(R.id.AboutIconButton);
		confirm = (Button) findViewById(R.id.buttonConfirm);
		name = (EditText) findViewById(R.id.editTextName);
		age = (EditText) findViewById(R.id.editTextAge);
		weight = (EditText) findViewById(R.id.editTextWeight);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		confirm.setOnClickListener(this);
		
		Intent intent = getIntent();
		newUser = intent.getBooleanExtra("newUser", false);
		if (newUser) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Create New Profile");
    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
    		builder.setMessage("Welcome to TrackFit! Please fill in your profile.");
    		AlertDialog dialog = builder.create();
    		dialog.show();
		} else {
			loadProfile();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(home)) {
			startActivity(new Intent(getApplicationContext(), MenuHome.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
			finish();
		} else if (v.equals(tracker)) {
			startActivity(new Intent(getApplicationContext(), MenuSportTrack.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
		} else if (v.equals(confirm)) {
			String message;
			if (newUser) {
				createUser();
				message = "User data has been created";
			} else {
				editUser();
				message = "User data has been saved";
			}
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
		}
	}
	
	private void editUser() {
		// TODO Auto-generated method stub
		String userName = name.getText().toString();
		int userAge = Integer.parseInt(age.getText().toString());
		int userWeight = Integer.parseInt(weight.getText().toString());
		int userHeight = 170;
		int bodyMass = profileController.calculateBodyMass(userWeight, userHeight);
				
		profileController.editUser(1, userName, userAge, userWeight, userHeight, bodyMass);
	}

	private void createUser() {
		// TODO Auto-generated method stub
		int id = 1;
		String userName = name.getText().toString();
		int userAge = Integer.parseInt(age.getText().toString());
		int userWeight = Integer.parseInt(weight.getText().toString());
		int userHeight = 170;
		int bodyMass = profileController.calculateBodyMass(userWeight, userHeight);
				
		profileController.setUser(id, userName, userAge, userWeight, userHeight, bodyMass);
	}
	
	private void loadProfile() {
		User user = profileController.getUser();
		name.setText(user.getName());
		age.setText(user.getAge());
		weight.setText(user.getWeight());
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
}
