package kazad_dum_2.types.angular;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glVertex3f;
import org.lwjgl.util.vector.Vector3f;

public class AngularDungeonCorridor implements AngularDungeonObject{
    float x, y, z, x2, y2, z2;
    int rx,ry,rz;
    Rectangle r;

    public AngularDungeonCorridor(float x, float y, float z, float x2, float y2, float z2, int l, int rx, int ry, int rz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
    }

    @Override
    public boolean isRoom() {
        return false;
    }
    @Override
    public void draw(){
//        glLineWidth(10);
//        glColor3f(1,0,1);
        glBegin(GL_LINES);
        glVertex3f(x,y,z);
        glVertex3f(getEndPoint().x, getEndPoint().y, getEndPoint().z);
        glEnd();
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
        return -1;
    }
    
    public Vector3f getEndPoint(){
        Vector3f coord = new Vector3f(x2,y2,z2); //assuming there is no y angle
////        new_x = old_x + cos(angle) * distance;
////        new_y = old_y + sin(angle) * distance;
//        coord.setX((float) (coord.getX() + Math.cos(rx) * l));
//        coord.setZ((float) (coord.getZ() + Math.cos(rz) * l));
        return coord;
    }
    public Vector3f getPosition(){
        return new Vector3f(x,y,z);
    }
    public void drawAsStairs(float stepheight){
        List<Vector3f> centerpoints = new ArrayList<>();
        boolean done = false;
        while (!done){
            
        }
    }
}
