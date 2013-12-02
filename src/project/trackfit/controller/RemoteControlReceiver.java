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
	    	Toast.makeText(context, "earphone activity", Toast.LENGTH_SHORT).show();
	    	if(intent.getExtras().getInt("state")==1){
	    		
	    		Toast.makeText(context, "earphones plugged", Toast.LENGTH_LONG).show();
	    	}
	    	else
	    		Toast.makeText(context, "earphones un-plugged", Toast.LENGTH_LONG).show();
	    }
	    /*
	    else {
	    	if(intentAction.equals(Intent.ACTION_MEDIA_BUTTON)) {
	    		Toast.makeText(context, "button pressed",Toast.LENGTH_LONG).show();
	            Toast.makeText(context, intent.getExtras().getString("EXTRA_KEY_EVENT"),Toast.LENGTH_LONG).show();
	    	}
	    }
	    */
	    
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
	    // do something
	        Toast.makeText(context, "BUTTON PRESSED!", Toast.LENGTH_SHORT).show();
	        act.speak();
	    }
	    abortBroadcast();
	}
}