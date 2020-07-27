package com.demo.helloopengl.jayway;
/**
 * Copyright 2010 Per-Erik Bergman (per-erik.bergman@jayway.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements Renderer {
    private Square mSquare;

    public OpenGLRenderer() {
        // Initialize our square.
        mSquare = new Square();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
     * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
     */
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        /*
            Name
                glClearColor — specify clear values for the color buffers

            Parameters
                red, green, blue, alpha
                Specify the red, green, blue, and alpha values used when the color buffers are cleared. The initial values are all 0.
            Description
                glClearColor specifies the red, green, blue, and alpha values used by glClear to clear the color buffers.
                Values specified by glClearColor are clamped to the range 0 1
        */
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);


        /*
            Name
                glClearDepthf — specify the clear value for the depth buffer
            Parameters
                depth
                Specifies the depth value used when the depth buffer is cleared. The initial value is 1.
            Description
                glClearDepthf specifies the depth value used by glClear to clear the depth buffer. Values specified by glClearDepthf are clamped to the range 0 1 .
        */
        gl.glClearDepthf(1.0f);

        // Enable Smooth Shading, default not really needed.
        gl.glShadeModel(GL10.GL_SMOOTH);

        /*
            Name
                glEnable — enable or disable server-side GL capabilities

            Parameters
                 cap
                Specifies a symbolic constant indicating a GL capability.
            Description
                glEnable and glDisable enable and disable various capabilities. Use glIsEnabled or glGet to determine the current setting of any capability. The initial value for each capability with the exception of GL_DITHER is GL_FALSE. The initial value for GL_DITHER is GL_TRUE.
                Both glEnable and glDisable take a single argument, cap, which can assume one of the following values:
            GL_BLEND
                If enabled, blend the computed fragment color values with the values in the color buffers. See glBlendFunc.
            GL_CULL_FACE
                If enabled, cull polygons based on their winding in window coordinates. See glCullFace.
            GL_DEPTH_TEST
                If enabled, do depth comparisons and update the depth buffer. Note that even if the depth buffer exists and the depth mask is non-zero, the depth buffer is not updated if the depth test is disabled. See glDepthFunc and glDepthRangef.
            GL_DITHER
                If enabled, dither color components or indices before they are written to the color buffer.
            GL_POLYGON_OFFSET_FILL
                If enabled, an offset is added to depth values of a polygon's fragments produced by rasterization. See glPolygonOffset.
            GL_SAMPLE_ALPHA_TO_COVERAGE
                If enabled, compute a temporary coverage value where each bit is determined by the alpha value at the corresponding sample location. The temporary coverage value is then ANDed with the fragment coverage value.
            GL_SAMPLE_COVERAGE
                If enabled, the fragment's coverage is ANDed with the temporary coverage value. If GL_SAMPLE_COVERAGE_INVERT is set to GL_TRUE, invert the coverage value. See glSampleCoverage.
            GL_SCISSOR_TEST
        If enabled, discard fragments that are outside the scissor rectangle. See glScissor.
        GL_STENCIL_TEST
        If enabled, do stencil testing and update the stencil buffer. See glStencilFunc and glStencilOp.
        */
        gl.glEnable(GL10.GL_DEPTH_TEST);

        /*
            Name
                glDepthFunc — specify the value used for depth buffer comparisons
            Parameters
                func
                Specifies the depth comparison function. Symbolic constants GL_NEVER, GL_LESS, GL_EQUAL, GL_LEQUAL, GL_GREATER, GL_NOTEQUAL, GL_GEQUAL, and GL_ALWAYS are accepted. The initial value is GL_LESS.
            Description
                glDepthFunc specifies the function used to compare each incoming pixel depth value with the depth value present in the depth buffer. The comparison is performed only if depth testing is enabled. (See glEnable and glDisable of GL_DEPTH_TEST.)
                func specifies the conditions under which the pixel will be drawn. The comparison functions are as follows:
            GL_NEVER
                Never passes.
            GL_LESS
                Passes if the incoming depth value is less than the stored depth value.
            GL_EQUAL
                Passes if the incoming depth value is equal to the stored depth value.
            GL_LEQUAL
                Passes if the incoming depth value is less than or equal to the stored depth value.
            GL_GREATER
                Passes if the incoming depth value is greater than the stored depth value.
            GL_NOTEQUAL
                Passes if the incoming depth value is not equal to the stored depth value.
            GL_GEQUAL
                Passes if the incoming depth value is greater than or equal to the stored depth value.
            GL_ALWAYS
                Always passes.
            The initial value of func is GL_LESS. Initially, depth testing is disabled. If depth testing is disabled or no depth buffer exists, it is as if the depth test always passes.
        */
        gl.glDepthFunc(GL10.GL_LEQUAL);

        /*
            Name
                glHint — specify implementation-specific hints

            Parameters
                target
                Specifies a symbolic constant indicating the behavior to be controlled. GL_GENERATE_MIPMAP_HINT is accepted.
                mode
                Specifies a symbolic constant indicating the desired behavior. GL_FASTEST, GL_NICEST, and GL_DONT_CARE are accepted.
            Description
                Certain aspects of GL behavior, when there is room for interpretation, can be controlled with hints. A hint is specified with two arguments. target is a symbolic constant indicating the behavior to be controlled, and mode is another symbolic constant indicating the desired behavior. The initial value for each target is GL_DONT_CARE. mode can be one of the following:
            GL_FASTEST
                The most efficient option should be chosen.
            GL_NICEST
                The most correct, or highest quality, option should be chosen.
            GL_DONT_CARE
                No preference.
            Though the implementation aspects that can be hinted are well defined, the interpretation of the hints depends on the implementation. The hint aspects that can be specified with target, along with suggested semantics, are as follows:
            GL_GENERATE_MIPMAP_HINT
                Indicates the quality of filtering when generating mipmap images with glGenerateMipmap.
        */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
     * khronos.opengles.GL10)
     */
    public void onDrawFrame(GL10 gl) {
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        /*
        without using glLoadIdentity, square is drawn but quickly move further and further into screen
        OpenGL ES doesn’t reset the drawing point between the frames, so we have to done it
        */
        // Replace the current matrix with the identity matrix
        /*
            Name
                glLoadIdentity — replace the current matrix with the identity matrix

            Description
                glLoadIdentity replaces the current matrix with the identity matrix. It is semantically equivalent to calling glLoadMatrix with the identity matrix
                1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1
                but in some cases it is more efficient.
            Errors
                GL_INVALID_OPERATION is generated if glLoadIdentity is executed between the execution of glBegin and the corresponding execution of glEnd.
         */
        gl.glLoadIdentity();


        /*
        if we don't use glTranslatef, OpenGL ES render view from where current position is
        that is by default at 0,0,0 the position that the view port is located
        OpenGL ES don’t render the things that are too close to the view port
        */
        // Translates 4 units into the screen
        /*
            Name
                glTranslate — multiply the current matrix by a translation matrix

            Parameters
                x, y, z
                Specify the x, y, and z coordinates of a translation vector.
            Description
                glTranslate produces a translation by x y z .
                The current matrix (see glMatrixMode) is multiplied by this translation matrix, with the product replacing the current matrix, as if glMultMatrix were called with the following matrix for its argument:
                1 0 0 x 0 1 0 y 0 0 1 z 0 0 0 1

                If the matrix mode is either GL_MODELVIEW or GL_PROJECTION, all objects drawn after a call to glTranslate are translated.
                Use glPushMatrix and glPopMatrix to save and restore the untranslated coordinate system.
            Errors
                GL_INVALID_OPERATION is generated if glTranslate is executed between the execution of glBegin and the corresponding execution of glEnd.
         */
        gl.glTranslatef(0, 0, -10);

        // Draw our square
        mSquare.draw(gl);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
     * .khronos.opengles.GL10, int, int)
     */
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        /*
            Name
            glMatrixMode — specify which matrix is the current matrix

            Parameters
                mode
                Specifies which matrix stack is the target for subsequent matrix operations. Three values are accepted: GL_MODELVIEW, GL_PROJECTION, and GL_TEXTURE. The initial value is GL_MODELVIEW. Additionally, if the ARB_imaging extension is supported, GL_COLOR is also accepted.
            Description
                glMatrixMode sets the current matrix mode. mode can assume one of four values:
            GL_MODELVIEW
                Applies subsequent matrix operations to the modelview matrix stack.
            GL_PROJECTION
                Applies subsequent matrix operations to the projection matrix stack.
            GL_TEXTURE
                Applies subsequent matrix operations to the texture matrix stack.
            GL_COLOR
                Applies subsequent matrix operations to the color matrix stack.
            To find out which matrix stack is currently the target of all matrix operations, call glGet with argument GL_MATRIX_MODE. The initial value is GL_MODELVIEW.
         */
        gl.glMatrixMode(GL10.GL_PROJECTION);

        // Reset the projection matrix
        gl.glLoadIdentity();

        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
                100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }
}
