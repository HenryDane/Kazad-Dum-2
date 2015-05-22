/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.angular;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Henry Dane
 */
public interface AngularDungeonObject {
    boolean isRoom();
    void draw();
    Rectangle2D getRect();
    void setX(float x);
    void setY(float y);
    void setZ(float z);
    float getX();
    float getY();
    float getZ();
    int getUUID();
}
