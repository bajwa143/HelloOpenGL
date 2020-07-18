package com.demo.helloopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

// Android application with OpenGL ES is similar to other android activities with UI.
// But instead of adding button, textview etc, we use GLSurfaceView for display
public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        glSurfaceView = new MyGLSurfaceView(this);
        setContentView(glSurfaceView);

    }

    /*
        A GLSurfaceView is a specialized view where you can draw OpenGL ES graphics.
        It does not do much by itself. The actual drawing of objects is controlled in the
        GLSurfaceView.Renderer that you set on this view. In fact, the code for this object
        is so thin, you may be tempted to skip extending it and just create an unmodified
        GLSurfaceView instance, but don’t do that. You need to extend this class in order
        to capture touch events, which is covered in the Respond to touch events lesson
     */
    class MyGLSurfaceView extends GLSurfaceView {
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private MyRenderer renderer;

        // In order to make your OpenGL ES application respond to touch events,
        // you must implement the onTouchEvent() method in your GLSurfaceView class
        private float previousX;
        private float previousY;
        public MyGLSurfaceView(Context context) {
            super(context);

            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);  // Here actually we set the OpenGL ES version we want to work with

            renderer = new MyRenderer();
            // set the renderer for drawing on GLSurfaceView
            setRenderer(renderer);

            // Render the view only when there is a change in the drawing data
            /*
            This setting prevents the GLSurfaceView frame from being redrawn until you call requestRender(),
            which is more efficient for this sample app
            */
            // To enable continuous rendering, disable render mode
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - previousX;
                    float dy = y - previousY;

                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2) {
                        dx = dx * -1;
                    }

                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2) {
                        dy = dy * -1;
                    }

                    renderer.setAngle(
                            renderer.getAngle() +
                                    ((dx + dy) * TOUCH_SCALE_FACTOR));
                    // tell renderer to draw frame
                    requestRender();
            }

            previousX = x;
            previousY = y;
            return true;
        }
    }
}
