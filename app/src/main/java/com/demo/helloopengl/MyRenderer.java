package com.demo.helloopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// Renderer class controls what gets drawn on the GLSurfaceView with which it is associated.
public class MyRenderer implements GLSurfaceView.Renderer {

    // Called once to set up the view's OpenGL ES environment
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    // Called if the geometry of the view changes, for example when the device's screen orientation changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    // Called for each redraw of the view
    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
    /*
    NOTE:
     We have GL10 parameter in methods, when you are using the OpengGL ES 2.0 APIs.
     These method signatures are simply reused for the 2.0 APIs to keep the Android framework code simpler.
    */
}
