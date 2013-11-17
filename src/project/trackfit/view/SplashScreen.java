package project.trackfit.view;

import project.trackfit.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
	        public void run() {
                startActivity(new Intent(SplashScreen.this, MenuHome.class));
                finish();
	        }
        }, secondsDelayed * 2000);
    }
    
}
