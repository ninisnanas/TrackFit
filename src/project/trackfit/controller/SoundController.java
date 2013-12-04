package project.trackfit.controller;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class SoundController {
	
	private Activity act;
	private MediaPlayer player;
	
	public SoundController(Activity act){
		this.act = act;
		player = new MediaPlayer();
	}
	
	public void PlaySound(String str){
		if (str.equals("NOTCLEAR")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/notclear.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (str.equals("WRONGCOMMAND")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/wrongcommand.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (str.equals("START")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/start.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (str.equals("PAUSE")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/pause.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (str.equals("RESUME")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/resume.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (str.equals("STOP")){
			try {
				AssetFileDescriptor afd = act.getAssets().openFd("sound/stop.mp3");
				player.reset();
				player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
				player.prepare();
				player.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
