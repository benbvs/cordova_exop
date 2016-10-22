package org.apache.cordova.estat;

import com.google.android.exoplayer2.*;
//import fr.mediametrie.mesure.library.android.*;
//import com.squareup.tape.*;
import org.json.JSONArray;
import org.json.JSONException;
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
 
public class estat extends CordovaPlugin {
 
  public Boolean trackerStarted = false;
  private SimpleExoPlayer exoPlayer;
  public String serial = "";
  
  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;
  
  
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if("initEstat".equals(action)){
      String id = args.getString(0);
	  if(!trackerStarted) { Handler handler = new Handler();
			TrackSelector trackSelector = new DefaultTrackSelector(handler);
			LoadControl loadControl = new DefaultLoadControl();
			exoPlayer = ExoPlayerFactory.newSimpleInstance(callbackContext, trackSelector, loadControl); }
      callbackContext.success();
      return true;
    }else if("sendHitEstat".equals(action)){
		if(args.length() == 1)
		{			
			Uri audioUri = Uri.parse("http://stream.basso.fi:8000/stream");
			DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ExoPlayerDemo");
			ExtractorsFactory extractor = new DefaultExtractorsFactory();
			MediaSource audioSource = new ExtractorMediaSource(audioUri, dataSourceFactory, extractor, null, null);
			exoPlayer.prepare(audioSource);
			exoPlayer.setPlayWhenReady(true);
			callbackContext.success();
		}
      return true;
    } else {
      callbackContext.error("AlertPlugin."+action+" not found !");
      return false;
    }
  }
  
   
}
