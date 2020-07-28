package com.demo.helloopengl.jayway;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GLES20Activity extends AppCompatActivity {

    //private GLSurfaceView mSurfaceView;
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (hasGLES20()) {
            mGLView = new GLSurfaceView(this);
            // Inform the default EGLContextFactory and default EGLConfigChooser which EGLContext client version to pick
            //mGLView.setEGLContextClientVersion(2);        // disable for second tutorial to run
            mGLView.setPreserveEGLContextOnPause(true);
            //mGLView.setRenderer(new GLES20Renderer());
            //mGLView.setRenderer(new OpenGLRenderer());
            mGLView.setRenderer(new OpenGLRenderer3());
        } else {
            // Time to get a new phone, OpenGL ES 2.0 not
            // supported.
        }

        setContentView(mGLView);
    }

    // to check if device support OpenGL 2.0
    private boolean hasGLES20() {
        ActivityManager activityManager = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * The activity must call the GL surface view's
         * onResume() on activity onResume().
         */
        if (mGLView != null) {
            // Inform the view that the activity is resumed. The owner of this view must call this method when
            // the activity is resumed. Calling this method will recreate the OpenGL display and resume the
            // rendering thread. Must not be called before a renderer has been set
            mGLView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        /*
         * The activity must call the GL surface view's
         * onPause() on activity onPause().
         */
        // Inform the view that the activity is paused
        // Calling this method will pause the rendering thread
        if (mGLView != null) {
            mGLView.onPause();
        }
    }
}
