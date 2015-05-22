/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import kazad_dum_2.util.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author henryjmo
 */
public class DungeonTile{ // originally implemented mappable
    // private Texture dirt;
    public float x, y, w, h, t;
    public boolean cubic, drawable;
    public int type;

    public DungeonTile(float x, float y, float w, float h, float t) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.t = t;
        cubic = false;
        drawable = true;
        Random r = new Random();
        this.type = r.nextInt((5 - 1) + 1) + 1;
//        try {
//            m = OBJLoader.loadModel(new File("src/kazad_dum/res/wallcube.obj"));
//        } catch (IOException ex) {
//            System.out.println("Couldnt find file");
//        }
    }

    public DungeonTile(float x, float y, float w, float h, boolean cube) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        cubic = cube;
        drawable = true;
        Random r = new Random();
        this.type = r.nextInt((5 - 1) + 1) + 1;
//        try {
//            m = OBJLoader.loadModel(new File("src/kazad_dum/res/wallcube.obj"));
//        } catch (IOException ex) {
//            System.out.println("Couldnt find file");
//        }
    }

    public DungeonTile(float x, float y, float w, float h, boolean cube, int type) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        cubic = cube;
        drawable = true;
        this.type = type;
//        try {
//            m = OBJLoader.loadModel(new File("src/kazad_dum/res/wallcube.obj"));
//        } catch (IOException ex) {
//            System.out.println("Couldnt find file");
//        }
    }

    public void draw() {
        //cubic = false;
        if (cubic) {
            drawCube(0, 1f);
//            drawnewcube();
        } else {
            glBegin(GL_QUADS);
            glColor4f(1f,1f,1f,.5f);
            glNormal3f(0, 1, 0);
            glTexCoord2f(0, 0);
            glVertex3f(x, 0, y);
            glTexCoord2f(1, 0);
            glVertex3f(x + w, 0, y);
            glTexCoord2f(1, 1);
            glVertex3f(x + w, 0, y + h);
            glTexCoord2f(0, 1);
            glVertex3f(x, 0, y + h);

            glNormal3f(0, -1, 0);
            glTexCoord2f(0, 0);
            glVertex3f(x, 1, y);
            glTexCoord2f(1, 0);
            glVertex3f(x + w, 1, y);
            glTexCoord2f(1, 1);
            glVertex3f(x + w, 1, y + h);
            glTexCoord2f(0, 1);
            glVertex3f(x, 1, y + h);
            glEnd();

        }

    }

    public void draw(float z) {

        //cubic = false;
        if (type != 8 && type != 5) {
            if (cubic) {
                drawCube(z, 1f);
            } else {
                glBegin(GL_QUADS);
                glNormal3f(0, 1, 0);
                glTexCoord2f(0, 0);
                glVertex3f(x, z, y);
                glTexCoord2f(1, 0);
                glVertex3f(x + w, z, y);
                glTexCoord2f(1, 1);
                glVertex3f(x + w, z, y + h);
                glTexCoord2f(0, 1);
                glVertex3f(x, z, y + h);
                glEnd();
            }
        } else {
            drawCube(z, 1f);

        }

    }

    private void drawCube(float z, float s) {
        glPushMatrix();
        glTranslatef(x, z, y);
        glScalef(s, s, s);
        glBegin(GL_QUADS);
//      Bottom Face
        glNormal3f(0, -1, 0);
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
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return 0;
    }
    public float getZ() {
        return y;
    }
    public float getW() {
        return 1;
    }
    public float getH() {
        return 1;
    }
    public float getD() {
        return 1;
    }
    public String className() {
        return "DungeonTile";
    }
}
