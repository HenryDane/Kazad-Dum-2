/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.types.standard;

/**
 *
 * @author henryjmo
 */
public enum Terrain {
    tileUnused, tileDirtWall, tileDirtFloor, tileStoneWall, tileCorridor, tileDoor, tileUpStairs, tileDownStairs, tileChest;
    
    /**
     *
     * @return
     */
    public int toInt(){
        switch (this){
            case tileUnused: return 0; //nondrawable
            case tileDirtWall: return 1;  //cubic
            case tileDirtFloor: return 2; //flat
            case tileStoneWall: return 3; // cubic
            case tileCorridor: return 4; //flat
            case tileDoor: return 5; // flat
            case tileUpStairs: return 6; //flat
            case tileDownStairs: return 7; //flat
            case tileChest: return 8; //flat
        }
        
        return -1;
    } 
    
    @Override
    public String toString(){
        switch (this){
            case tileUnused: return " ";
            case tileDirtWall: return "1";
            case tileDirtFloor: return "+";
            case tileStoneWall: return "3";
            case tileCorridor: return "4";
            case tileDoor: return"5";
            case tileUpStairs: return "6";
            case tileDownStairs: return "7";
            case tileChest: return"8";
        }
        
        return "-1";
    }
}
