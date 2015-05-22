package kazad_dum_2.types.angular;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.lwjgl.util.vector.Vector3f;

public class AngularDungeonRoom implements AngularDungeonObject {
    public float x,y,z;
    public int uuid;
    int rx,ry,rz,w,h,d;
    Rectangle2D r;

    public AngularDungeonRoom(float x, float y, float z, int rx, int ry, int rz, int w, int h, int d, int uuid) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.w = w;
        this.h = h;
        this.d = d;
        this.uuid = uuid;
        r = new Rectangle2D.Float(x,y,w,h);
    }
    
    @Override
    public boolean isRoom() {
        return true;
    }
    @Override
    public void draw() {
        glColor4f(1f,1f,1f,.5f);
        glPushMatrix();
        glRotatef(rx, 1.0f, 0.0f, 0.0f);   //X
        glRotatef(ry, 0.0f, 1.0f, 0.0f);   //Y
        glRotatef(rz, 0.0f, 0.0f, 1.0f);   //Z
        glBegin(GL_QUADS);
        glNormal3f(0,1,0);
        glVertex3f(x,y,z);
        glVertex3f(x+w,y,z);
        glVertex3f(x+w,y,z+h);
        glVertex3f(x,y,z+h);
        
        glNormal3f(0,-1,0);
        glVertex3f(x,y+d,z);
        glVertex3f(x+w,y+d,z);
        glVertex3f(x+w,y+d,z+h);
        glVertex3f(x,y+d,z+h);
        glEnd();
        glPopMatrix();
    }
    @Override
    public Rectangle2D getRect() {
        return r;
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
    public int getUUID() {
        return uuid;
    }
    
    public Vector3f getDockCoord(int side, int offset){ //FIX THIS!!!
        switch (side){
            case 1:
                return new Vector3f(x+(.5f*w)+offset,y,z+h);
            case 2:
                return new Vector3f(x,y,z+(.5f*h)+offset);
            case 3:
                return new Vector3f(x+(.5f*w)+offset,y,z);
            case 4:
                return new Vector3f(x,y,z+(.5f*h)+offset);
            default:
                
        }
        return null;
    }
    public Vector3f getPosition(){
        return new Vector3f(x,y,z);
    }
}