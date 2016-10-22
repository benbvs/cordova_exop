package org.apache.cordova.estat;

import com.google.android.exoplayer;
//import fr.mediametrie.mesure.library.android.*;
import com.squareup.tape.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
 
public class estat extends CordovaPlugin {
 
  public Boolean trackerStarted = false;
  private ExoPlayer exoPlayer;
  public String serial = "";
  
  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;
  
  
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if("initEstat".equals(action)){
      String id = args.getString(0);
	  if(!trackerStarted) { exoPlayer = ExoPlayer.Factory.newInstance(1); }
      callbackContext.success();
      return true;
    }else if("sendHitEstat".equals(action)){
		if(args.length() == 1)
		{
			// String with the url of the radio you want to play
			String url = args.getString(0);
			Uri radioUri = Uri.parse(url);
			// Settings for exoPlayer
			Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
			String userAgent = Util.getUserAgent(context, "ExoPlayerDemo");
			DataSource dataSource = new DefaultUriDataSource(context, null, userAgent);
			ExtractorSampleSource sampleSource = new ExtractorSampleSource(
			radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
			audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
			// Prepare ExoPlayer
			exoPlayer.prepare(audioRenderer);
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
