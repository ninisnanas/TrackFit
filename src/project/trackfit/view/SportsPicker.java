package project.trackfit.view;

import project.trackfit.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SportsPicker extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports_picker);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sports_picker, menu);
		return true;
	}

}
