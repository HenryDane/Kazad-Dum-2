/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

/**
 *
 * @author Henry Danes
 */
public interface Placeable {
    public float getX();
    public float getY();
    public float getZ();
    public float getScale();
    public PlaceableType getType();
    public void setX(float x);
    public void setY(float y);
    public void setZ(float z);
    public void setScale(float scale);
    public void draw();
}
