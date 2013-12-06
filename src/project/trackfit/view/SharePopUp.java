package project.trackfit.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.brickred.socialauth.android.DialogListener;
import org.brickred.socialauth.android.SocialAuthAdapter;
import org.brickred.socialauth.android.SocialAuthError;
import org.brickred.socialauth.android.SocialAuthListener;
import org.brickred.socialauth.android.SocialAuthAdapter.Provider;

import project.trackfit.R;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class SharePopUp extends Activity implements OnClickListener {
	
	private Button confirmButton;
	private Button cancelButton;
	private SocialAuthAdapter adapter;
	private Bitmap bitmap;
	private String shareMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_pop_up);
		
		System.out.println("ini share popup");
		
		confirmButton = (Button) findViewById(R.id.yesButton);
		cancelButton = (Button) findViewById(R.id.noButton);
		
		cancelButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		shareMessage = intent.getStringExtra("message");
		//FileInputStream in = new FileInputStream("/" + Environment.getExternalStorageDirectory().getPath() + "/"+"trackFit2"+".png");
		bitmap = BitmapFactory.decodeFile("/" + Environment.getExternalStorageDirectory().getPath() + "/"+"trackFit2"+".png");
		
		adapter = new SocialAuthAdapter(new ResponseListener());
		adapter.addProvider(Provider.FACEBOOK, R.drawable.facebook);
		adapter.addProvider(Provider.TWITTER, R.drawable.twitter);
		adapter.addProvider(Provider.MYSPACE, R.drawable.myspace);
		adapter.addProvider(Provider.YAHOO, R.drawable.yahoo);
		adapter.addProvider(Provider.GOOGLE, R.drawable.google);
		adapter.addProvider(Provider.GOOGLEPLUS, R.drawable.googleplus);
		//adapter.addProvider(Provider.INSTAGRAM, R.drawable.i)
		adapter.addProvider(Provider.FOURSQUARE, R.drawable.foursquare);
		
		adapter.addCallBack(Provider.TWITTER, "http://socialauth.in/socialauthdemo/socialAuthSuccessAction.do");
		adapter.enable(confirmButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_pop_up, menu);
		return true;
	}

	private final class ResponseListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {

			Log.d("ShareButton", "Authentication Successful");

			// Get name of provider after authentication
			String providerName = values.getString(SocialAuthAdapter.PROVIDER);
			Log.d("ShareButton", "Provider Name = " + providerName);
			Toast.makeText(SharePopUp.this, providerName + " connected", Toast.LENGTH_LONG).show();

			// Please avoid sending duplicate message. Social Media Providers
			// block duplicate messages.
			
			try {
				if (bitmap != null) {
					adapter.uploadImageAsync(shareMessage, "Track.png", bitmap, 0, new UploadImageListener());
				} else Toast.makeText(SharePopUp.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//adapter.updateStatus(getShare(), new MessageListener(), false);
			finish();
		}

		private String getShare() {
			// TODO Auto-generated method stub
			return shareMessage;
		}

		@Override
		public void onError(SocialAuthError error) {
			Log.d("ShareButton", "Authentication Error: " + error.getMessage());
		}

		@Override
		public void onCancel() {
			Log.d("ShareButton", "Authentication Cancelled");
		}

		@Override
		public void onBack() {
			Log.d("Share-Button", "Dialog Closed by pressing Back Key");
		}
	}
	
	// To get status of message after authentication
	private final class MessageListener implements SocialAuthListener<Integer> {
		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			if (status.intValue() == 200 || status.intValue() == 201 || status.intValue() == 204)
				Toast.makeText(SharePopUp.this, "Message posted on " + provider, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(SharePopUp.this, "Message not posted on " + provider, Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(SocialAuthError e) {

		}
	}
	
	private final class UploadImageListener implements SocialAuthListener<Integer> {

		@Override
		public void onExecute(String provider, Integer t) {
			Integer status = t;
			Log.d("Custom-UI", String.valueOf(status));
			if (status.intValue() == 200 || status.intValue() == 201
					|| status.intValue() == 204)
				Toast.makeText(SharePopUp.this, "Image Uploaded",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(SharePopUp.this,
						"Image not Uploaded", Toast.LENGTH_SHORT).show();
		}
	
		@Override
		public void onError(SocialAuthError e) {
	
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(cancelButton)) finish();
	}
}
