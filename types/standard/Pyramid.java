/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 *
 * @author Henry Danes
 */
public class Pyramid implements Placeable {

    float x, y, z, s;
    final PlaceableType type = PlaceableType.Pyramid;

    public Pyramid(float scale, float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.s = s;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getScale() {
        return s;
    }

    public void setScale(float s) {
        this.s = s;
    }

    public PlaceableType getType() {
        return type;
    }

    public void draw() {
        glPushMatrix();
        glTranslatef(x, y, z);
        glScalef(s, s, s);
        glBegin(GL_TRIANGLES);           // Begin drawing the pyramid with 4 triangles
        // Front
//      glColor3f(1.0f, 0.0f, 0.0f);     // Red
        glVertex3f(0.0f, 1.0f, 0.0f);
//      glColor3f(0.0f, 1.0f, 0.0f);     // Green
        glVertex3f(-1.0f, -1.0f, 1.0f);
//      glColor3f(0.0f, 0.0f, 1.0f);     // Blue
        glVertex3f(1.0f, -1.0f, 1.0f);

        // Right
//      glColor3f(1.0f, 0.0f, 0.0f);     // Red
        glVertex3f(0.0f, 1.0f, 0.0f);
//      glColor3f(0.0f, 0.0f, 1.0f);     // Blue
        glVertex3f(1.0f, -1.0f, 1.0f);
//      glColor3f(0.0f, 1.0f, 0.0f);     // Green
        glVertex3f(1.0f, -1.0f, -1.0f);

        // Back
//      glColor3f(1.0f, 0.0f, 0.0f);     // Red
        glVertex3f(0.0f, 1.0f, 0.0f);
//      glColor3f(0.0f, 1.0f, 0.0f);     // Green
        glVertex3f(1.0f, -1.0f, -1.0f);
//      glColor3f(0.0f, 0.0f, 1.0f);     // Blue
        glVertex3f(-1.0f, -1.0f, -1.0f);

        // Left
//      glColor3f(1.0f,0.0f,0.0f);       // Red
        glVertex3f(0.0f, 1.0f, 0.0f);
//      glColor3f(0.0f,0.0f,1.0f);       // Blue
        glVertex3f(-1.0f, -1.0f, -1.0f);
//      glColor3f(0.0f,1.0f,0.0f);       // Green
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glEnd();   // Done drawing the pyramid
        glPopMatrix();
        glColor4f(1, 1, 1, 1f);
    }

}
