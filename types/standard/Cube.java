/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

/**
 *
 * @author Henry Danes
 */
public class Cube implements Placeable{
    public float s,x,y,z;
    public final PlaceableType type = PlaceableType.Cube;

    public Cube(float s, float x, float y, float z) {
        this.s = s;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getScale() {
        return s;
    }

    @Override
    public PlaceableType getType() {
        return type;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void setScale(float scale) {
        this.s = scale;
    }
    
    public void draw(){
        glPushMatrix();
        glTranslatef(x, y, z);
        glScalef(s, s, s);

        glBegin(GL_QUADS);
//      Top Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0, 1, 0);
        glTexCoord2f(1, 0);
        glVertex3f(1, 1, 0);
        glTexCoord2f(1, 1);
        glVertex3f(1, 1, 1);
        glTexCoord2f(0, 1);
        glVertex3f(0, 1, 1);

//      Bottom Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1, 0);
        glVertex3f(1, 0, 0);
        glTexCoord2f(1, 1);
        glVertex3f(1, 0, 1);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 1);

//      Front Face
        glNormal3f(0.0f, 0.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0, 0, 1.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f, 0, 1.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0, 1.0f, 1.0f);
        // Back Face
        glNormal3f(0.0f, 0.0f, -1.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        // Right face
        glNormal3f(1.0f, 0.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 1.0f);
        // Left Face
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 1.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glEnd();

        glPopMatrix();
        glColor4f(1, 1, 1, 1f);
    }
    
}
