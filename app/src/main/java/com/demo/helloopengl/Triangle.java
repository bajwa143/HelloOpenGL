package com.demo.helloopengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/*
    OpenGL ES allows you to define drawn objects using coordinates in three-dimensional space.
    So, before you can draw a triangle, you must define its coordinates. In OpenGL, the typical
    way to do this is to define a vertex array of floating point numbers for the coordinates.
    For maximum efficiency, you write these coordinates into a ByteBuffer, that is passed into
    the OpenGL ES graphics pipeline for processing.
*/
public class Triangle {

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    //-----------------------------------------------------------------
    //                    (0.0,0.62200,0.0)
    //                    /\
    //                  /   \
    //                /      \
    //              /_________\ (0.5,-0.31100,0.0)
    //   (-0.5,-0.31100,0.0)
    //-----------------------------------------------------------------
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    /*
    Drawing a defined shape using OpenGL ES 2.0 requires a significant amount of code,
    because you must provide a lot of details to the graphics rendering pipeline
    */
    // OpenGL ES graphics code for rendering the vertices of a shape
    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" + // add a matrix variable to the vertex shader
                    "}";

    // OpenGL ES code for rendering the face of a shape with colors or textures
    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // Use to access and set the view transformation
    private int vPMatrixHandle;

    // An OpenGL ES object that contains the shaders you want to use for drawing one or more shapes
    private final int mProgram;
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};
    private FloatBuffer vertexBuffer;
    private int positionHandle;
    private int colorHandle;

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);

        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        // Sets this buffer's position. If the mark is defined and larger than the new position then it is discarded
        vertexBuffer.position(0);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

//------------------To Apply Camera Transformation---------------------
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);
//---------------------------------------------------------------------

        // Draw the triangle
        // first arguments tells to draw triangle
        // second argument tells OpenGL to read in vertices starting at the beginning of our vertex array
        // third argument is count (number of time to draw)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
/*
By default, OpenGL ES assumes a coordinate system where [0,0,0] (X,Y,Z) specifies the center of the GLSurfaceView
frame, [1,1,0] is the top right corner of the frame and [-1,-1,0] is bottom left corner of the frame
*/