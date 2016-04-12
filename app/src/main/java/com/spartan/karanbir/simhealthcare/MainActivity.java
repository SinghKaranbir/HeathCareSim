package com.spartan.karanbir.simhealthcare;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.vrtoolkit.cardboard.widgets.video.VrVideoEventListener;
import com.google.vrtoolkit.cardboard.widgets.video.VrVideoView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private VrVideoView mVrVideoView;
    private boolean loadVideoSuccessful = false;
    private Uri fileURI;
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileURI = Uri.parse(VideoURL);
        mVrVideoView = (VrVideoView) findViewById(R.id.vrvideoview);
        mVrVideoView.setEventListener(new ActivityEventListener());
        try {
            mVrVideoView.loadVideo(fileURI);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        // Prevent the view from rendering continuously when in the background.
        mVrVideoView.pauseRendering();
        // If the video was playing when onPause() iss called, the default behavior will be to pause
        // the video and keep it paused when onResume() is called.

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the 3D rendering.
        mVrVideoView.resumeRendering();

    }

    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        mVrVideoView.shutdown();
        super.onDestroy();
    }
    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrVideoEventListener {

        /**
         * Called by video widget on the UI thread when it's done loading the video.
         */
        @Override
        public void onLoadSuccess() {
            Log.i(TAG, "Sucessfully loaded video " + mVrVideoView.getDuration());
            loadVideoSuccessful = true;

        }

        /**
         * Called by video widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            // An error here is normally due to being unable to decode the video format.
            loadVideoSuccessful = false;
            Toast.makeText(
                    MainActivity.this, "Error loading video: " + errorMessage, Toast.LENGTH_LONG)
                    .show();
            Log.e(TAG, "Error loading video: " + errorMessage);
        }



        /**
         * Update the UI every frame.
         */
        @Override
        public void onNewFrame() {

        }

        /**
         * Make the video play in a loop. This method could also be used to move to the next video in
         * a playlist.
         */
        @Override
        public void onCompletion() {
            mVrVideoView.seekTo(0);
        }
    }
}
