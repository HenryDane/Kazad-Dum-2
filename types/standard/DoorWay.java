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
public class DoorWay {
    boolean horizontal;
    float width, height, x, y;

    public DoorWay(float x, float y, float width, float height, boolean horizontal) {
        this.horizontal = horizontal;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    
}
