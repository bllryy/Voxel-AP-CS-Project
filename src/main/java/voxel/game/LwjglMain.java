package voxel.game;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class LwjglMain 
{
    private long window;
    private int width = 1280, height = 720;
    private Camera camera = new Camera();

    private double lastX, lastY;
    private boolean dragging = false;

    public static void main(String[] args) 
    {
        new LwjglMain().run();
    }

    public void run() {
        init();
        loop();

        //
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void init()
    {
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Voxel - primitive", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");

        // Simple input callbacks
        glfwSetCursorPosCallback(window, (w, x, y) -> {
            if (dragging) {
                float dx = (float) (x - lastX);
                float dy = (float) (y - lastY);
                camera.addYaw(dx * 0.3f);
                camera.addPitch(-dy * 0.3f);
            }
            lastX = x; lastY = y;
        });

        glfwSetMouseButtonCallback(window, (w, button, action, mods) -> dragging = (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS));

        glfwSetScrollCallback(window, (w, xoffset, yoffset) -> camera.addDistance((float) -yoffset * 0.5f));

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.5f, 0.7f, 1.0f, 1.0f);

        // set viewport and projection once
        glViewport(0, 0, width, height);
        setupProjection();
    }

    private void setupProjection() 
    {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = (float) width / Math.max(1, height);
        float fov = 70f;
        float zNear = 0.1f;
        float top = (float) Math.tan(Math.toRadians(fov / 2)) * zNear;
        float right = top * aspect;
        glFrustum(-right, right, -top, top, zNear, 100f);
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop()
    {
        while (!glfwWindowShouldClose(window))
        {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            camera.applyView();

            // Draw simple ground
            glColor3f(0.4f, 0.6f, 0.3f);
            glBegin(GL_QUADS);
                glVertex3f(-50f, -1f, -50f);
                glVertex3f(50f, -1f, -50f);
                glVertex3f(50f, -1f, 50f);
                glVertex3f(-50f, -1f, 50f);
            glEnd();

            glfwSwapBuffers(window);
        }
    }

//    private void drawCube(float x, float y, float z, float size) {} ????

}
