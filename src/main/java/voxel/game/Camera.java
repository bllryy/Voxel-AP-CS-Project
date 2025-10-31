package voxel.game;

import static org.lwjgl.opengl.GL11.*;

public class Camera
{
    private float yaw = 45f;
    private float pitch = 20f;
    private float distance = 6f;

    public void addYaw(float d) { yaw += d; }
    public void addPitch(float d) { pitch = clamp(pitch + d, -89f, 89f); }
    public void addDistance(float d) { distance = Math.max(1f, distance + d); }

    private float clamp(float v, float a, float b) 
    {
        return Math.max(a, Math.min(b, v));
    }

    public void applyView()
    {
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        // camera sits behind the target: translate back by distance
        glTranslatef(0f, 0f, -distance);
        // pitch then yaw
        glRotatef(pitch, 1f, 0f, 0f);
        glRotatef(yaw, 0f, 1f, 0f);
        // raise camera slightly so it's a third-person
        glTranslatef(0f, -1f, 0f);
    }
}

