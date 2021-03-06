package com.benbvs.cordova.exoplayer;

import com.google.android.exoplayer2.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.LOG;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Handler;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import android.net.Uri;
 
public class exoplayer extends CordovaPlugin {
 
  public Boolean playerInitialised = false;
  private SimpleExoPlayer exoPlayer;
  
  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;
  
  
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if("initPlayer".equals(action)){
	  if(!playerInitialised) { 
		Handler handler = new Handler();
		TrackSelector trackSelector = new DefaultTrackSelector(handler);
		LoadControl loadControl = new DefaultLoadControl();
		exoPlayer = ExoPlayerFactory.newSimpleInstance(this.cordova.getActivity().getApplicationContext(), trackSelector, loadControl);
		exoPlayer.addListener(this);
		playerInitialised=true;
	  }
      	callbackContext.success();
      	return true;
    }else if("play".equals(action)){
		if(args.length() == 1 && playerInitialised){
			Uri audioUri = Uri.parse(args.getString(0));
			DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ExoPlayerDemo");
			ExtractorsFactory extractor = new DefaultExtractorsFactory();
			MediaSource audioSource = new ExtractorMediaSource(audioUri, dataSourceFactory, extractor, null, null);
			exoPlayer.prepare(audioSource);
			exoPlayer.setPlayWhenReady(true);
			callbackContext.success();
		}
      return true;
    }else if("stop".equals(action)){
		if(playerInitialised && exoPlayer.getPlayWhenReady()){			
			exoPlayer.stop();
			exoPlayer.seekTo(0L);
			//exoPlayer.release();
			callbackContext.success();
		}
      return true;
    } else {
      callbackContext.error("AlertPlugin."+action+" not found !");
      return false;
    }
  }
	
@Override
public void onPlayerStateChanged(boolean playWhenReady, int state) {
    Log.d("exoplayer", "onPlayerStateChanged + " + playbackState);
    if (state == ExoPlayer.STATE_ENDED){
        //player back ended
    }
}
  
   
}
