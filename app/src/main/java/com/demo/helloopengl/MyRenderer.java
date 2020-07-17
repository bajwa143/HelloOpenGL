package com.demo.helloopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

// Renderer class controls what gets drawn on the GLSurfaceView with which it is associated.
public class MyRenderer implements GLSurfaceView.Renderer {
    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];

    Triangle triangle;

    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    // Called once to set up the view's OpenGL ES environment
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // initialize a triangle
        triangle = new Triangle();

    }

    // Called for each redraw of the view
    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        // multiple projectionMatrix and viewMatrix and store result in vPMatrix
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        triangle.draw(vPMatrix);
    }

    // Called if the geometry of the view changes, for example when the device's screen orientation changes
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        // The data for a projection transformation is calculated in the onSurfaceChanged()
        // method of your GLSurfaceView.Renderer class

        // take height and width of the GLSurfaceView and uses it calculate ratio
        float ratio = (float) width / height;

        // populate a projection transformation Matrix
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    /*
    NOTE:
     We have GL10 parameter in methods, when you are using the OpengGL ES 2.0 APIs.
     These method signatures are simply reused for the 2.0 APIs to keep the Android framework code simpler.
    */
}
