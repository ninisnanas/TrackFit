package project.trackfit.controller;

import project.trackfit.view.MenuSportTrack;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class RemoteControlReceiver extends BroadcastReceiver {
	static MenuSportTrack act;
	
	public RemoteControlReceiver(MenuSportTrack act) {
		this();
		Log.d("debug", "konstruktor");
	    RemoteControlReceiver.act = act;
	}
	
	public RemoteControlReceiver() {
		super();
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
	    String intentAction = intent.getAction();
	    Log.d("debug", "intentAction = " + intentAction);
	    
	    if(intentAction.equals(Intent.ACTION_HEADSET_PLUG)) {
	    	if(intent.getExtras().getInt("state")==1){
	    		
	    		Toast.makeText(context, "Earphones plugged", Toast.LENGTH_LONG).show();
	    	}
	    	else
	    		Toast.makeText(context, "Earphones unplugged", Toast.LENGTH_LONG).show();
	    }
	    
	    if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
	    	Log.d("debug","event=nullaa");
	        return;
	    }
	    KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
	    if (event == null) {
	    	Log.d("debug","event=null");
	        return;
	    }
	    Log.d("debug","haha");
	    int action = event.getAction();
	    Log.d("debug", "getAction = " + action);
	    if (action == KeyEvent.ACTION_DOWN) {
	        act.speak();
	    }
	    abortBroadcast();
	}
}