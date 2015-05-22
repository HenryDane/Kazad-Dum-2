/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

import java.util.Arrays;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author henryjmo
 */
public class Tile {

    int x, y, w, h;
    public float d, z;
    public int t;
    // top, right, bottom, left
    int[] neighborsType;

    public Tile(int x, int y, int z, int w, int h, int d, int t) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.d = d;
        this.t = t;
    }

    public Tile(int x, int y, int z, int w, int h, int t) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.t = t;
        this.d = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return (int) z;
    }

    public int getT() {
        return t;
    }

    public Tile(int x, int y, int z, int w, int h, int d, int t, int[] neighborsType) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.z = z;
        this.h = h;
        this.d = d;
        this.t = t;
        this.neighborsType = neighborsType;
        // System.out.println(Arrays.toString(neighborsType));
    }

    public void draw() {
        switch (t){
            case 1: //dirt wall
                drawCube(0,1);
                break;
            case 2: //dirt floor
                drawPanels();
                break;
            case 3: //stone wall
                drawCube(0,1);
                break;
            case 4: //corridor
                drawPanels();
                break;
            case 5: //door 
                drawPanels();
                break;
            case 6: //up-stairs
                drawPanels();
                break;
            case 7: //down stairs
                drawCube(0,.2f);
                drawPanels();
                break;
            case 8: //chest
                drawCube(0,.2f);
                drawPanels();
                break;
            default: //nothing there
                drawCube(0,1);
                break;
        }
    }

    private void drawPanels() {
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

        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(x, z + d, y);
        glTexCoord2f(1, 0);
        glVertex3f(x + w, z + d, y);
        glTexCoord2f(1, 1);
        glVertex3f(x + w, z + d, y + h);
        glTexCoord2f(0, 1);
        glVertex3f(x, z + d, y + h);
        glEnd();
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
        glNormal3f(0.0f, 0.0f, 1.0f);
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
    
    public Vector3f getRepulsionNoY(Vector3f position){
        Vector3f repulsion = new Vector3f(0,0,0);
        System.out.println("Position: " + position.toString());
        
        Vector3f thisTile = new Vector3f(this.x + (this.w / 2), this.z, this.y + (this.h/2));
        System.out.println("This: " + thisTile.toString());
        if(position.x < thisTile.x){
            repulsion.x -= .1f;
        } else if (position.x > thisTile.x){
            repulsion.x += .1f;
        }
        
        if(position.z < thisTile.z){
            repulsion.z -= .1f;
        } else if (position.z > thisTile.z){
            repulsion.z += .1f;
        }
        System.out.println("Repulsion: " + repulsion.toString());
        
        return repulsion;
    }
}
