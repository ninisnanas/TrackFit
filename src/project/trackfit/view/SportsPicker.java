package project.trackfit.view;

import project.trackfit.R;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SportsPicker extends Activity {
	
	private RadioGroup rGroup;
	private RadioButton selectedAct;
	private Button confirmButton;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports_picker);
		context = this;
		
		rGroup = (RadioGroup) findViewById(R.id.radioGroupSportsPicker);
		confirmButton = (Button) findViewById(R.id.buttonConfirm);
		
		confirmButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int selected = rGroup.getCheckedRadioButtonId();
				selectedAct = (RadioButton) findViewById(selected);
				
				if (selectedAct != null) {
					Intent intent = new Intent(context, MenuSportTrack.class);
					intent.putExtra("activity", selectedAct.getText());
					startActivity(intent);
					finish();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        builder.setTitle("Attention!");
					builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					builder.setMessage("You haven't choose any activity! Choose one first!");
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sports_picker, menu);
		return true;
	}

}
