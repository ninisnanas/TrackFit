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
	Button bodyMass;
	EditText name;
	EditText age;
	EditText weight;
	EditText height;
	
	boolean newUser;
	boolean valid;
	User user;
	
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
		bodyMass = (Button) findViewById(R.id.buttonBodyMass);
		name = (EditText) findViewById(R.id.editTextName);
		age = (EditText) findViewById(R.id.editTextAge);
		weight = (EditText) findViewById(R.id.editTextWeight);
		height = (EditText) findViewById(R.id.editTextHeight);
		profile = (Button) findViewById(R.id.ProfileIconButton);
		
		home.setOnClickListener(this);
		tracker.setOnClickListener(this);
		history.setOnClickListener(this);
		dashboard.setOnClickListener(this);
		about.setOnClickListener(this);
		profile.setOnClickListener(this);
		confirm.setOnClickListener(this);
		bodyMass.setOnClickListener(this);
		
		Intent intent = getIntent();
		newUser = intent.getBooleanExtra("newUser", false);
		if (newUser) {
			user = new User();
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Create New Profile");
    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
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
		if (v.equals(home)) {
			if (!newUser) {
				startActivity(new Intent(getApplicationContext(), MenuHome.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
				finish();
			} else {
				showPopUp();
			}
		} else if (v.equals(tracker)) {
			if(!newUser) {
				startActivity(new Intent(getApplicationContext(), SportsPicker.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				finish();
			} else {
				showPopUp();
			}
		} else if (v.equals(history)) {
			if (!newUser) {
				startActivity(new Intent(getApplicationContext(), MenuActHistory.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				finish();
			} else {
				showPopUp();
			}
		} else if (v.equals(dashboard)) {
			if (!newUser) {
				startActivity(new Intent(getApplicationContext(), MenuDashDist.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				finish();
			} else {
				showPopUp();
			}
		} else if (v.equals(about)) {
			if (!newUser) {
				startActivity(new Intent(getApplicationContext(), MenuAbout.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
				finish();
			} else {
				showPopUp();
			}
		} else if (v.equals(confirm)) {
			String message;
			//validate();
			if (newUser) {
				valid = createUser();
				if (valid) {
					message = "User data has been created";
					newUser = false;
					Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				} else {
					showAlertPopUp();
				}
			} else {
				editUser();
				message = "User data has been saved";
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}
		} else if (v.equals(bodyMass)) {
			if (!newUser) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
	            builder.setTitle("Body Mass Index Check");
	    		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
	    		builder.setMessage("Based on your body mass index, you are " + profileController.getBMIDesc(user.getBodyMass()) + ".");
	    		AlertDialog dialog = builder.create();
	    		dialog.show();
			} else {
				showPopUp();
			}
		}
	}

	private void showAlertPopUp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Attention!");
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setMessage("You haven't fill all the fields! Complete all the fields!");
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void showPopUp() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Attention!");
		builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.setMessage("You haven't finished creating your profile! Confirm first!");
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private boolean createUser() {
		try {
			user.setName(name.getText().toString());
			user.setAge(Integer.parseInt(age.getText().toString()));
			user.setWeight(Integer.parseInt(weight.getText().toString()));
			user.setHeight(Integer.parseInt(height.getText().toString()));
			user.setBodyMass(profileController.calculateBodyMass(user.getWeight(), user.getHeight()));
					
			return profileController.setUser(1, user.getName(), user.getAge(), user.getWeight(), user.getHeight(), user.getBodyMass());
		} catch (NumberFormatException e) {
			System.err.println("error: " + e);
			return false;
		}
	}
	
	private void editUser() {
		user.setName(name.getText().toString());
		user.setAge(Integer.parseInt(age.getText().toString()));
		user.setWeight(Integer.parseInt(weight.getText().toString()));
		user.setHeight(Integer.parseInt(height.getText().toString()));
		user.setBodyMass(profileController.calculateBodyMass(user.getWeight(), user.getHeight()));
				
		profileController.editUser(1, user.getName(), user.getAge(), user.getWeight(), user.getHeight(), user.getBodyMass());
	}
	
	private void loadProfile() {
		user = profileController.getUser();
		name.setText(user.getName());
		age.setText(user.getAge() + "");
		weight.setText(user.getWeight() + "");
		height.setText(user.getHeight() + "");
	}

	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,0);
	}
}
