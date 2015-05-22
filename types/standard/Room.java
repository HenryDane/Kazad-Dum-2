/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Henry Danes
 */
public class Room {
    public float x, y, z, w, h, d;
    public boolean barrels, rocks, bones;
    List<DoorWay> doors = new ArrayList<DoorWay>();

    public Room(float x, float y, float z, float w, float h, float d, boolean barrels, boolean rocks, boolean bones) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
        this.d = d;
        this.barrels = barrels;
        this.rocks = rocks;
        this.bones = bones;
    }
    
    public void draw(){
        //Dont even ask me about this
    }
    
    public String display(){
        String s = "";
        
        s = "X: " + x + " Y: " + y + " Z: " + z + " W: " + w + " H: " + h + " D: " + d;
        
        return s;
    }
}
