/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.algorithims;

import java.util.ArrayList;
import java.util.List;
import kazad_dum_2.types.standard.Room;
import kazad_dum_2.types.standard.Tile;

/**
 *
 * @author Henry Danes
 */
public class StandardDungeonUnifier {

    int[] map;
    int xSize;
    int ySize;
    List<Room> rooms = new ArrayList<Room>();

    public StandardDungeonUnifier(int[] map, int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.map = map;
    }

    public void discoverRooms() {
        int maxx = xSize;
        int maxy = ySize;
        boolean done = false;
        while (!done) {
            int it = 0;
            int jt = 0;
            loopfirst:
            for (int i = 0; i < maxx; i++) {
                for (int j = 0; j < maxy; j++) {
                    if (getCell(i, j) == 3 || getCell(i, j) == 1) {
                        it = i;
                        jt = j;
                        break loopfirst;
                    } else {
                        //do nothing
                    }
                }
            }

            boolean workingi = true;
            boolean workingj = true;
            for (int i = it; workingi; i++) {
                for (int j = jt; workingj; j++) {
                    if (getCell(i, j) == 1 || getCell(i, j) == 3 || getCell(i, j) == 0) {
                        workingi=false;
                    }
                }
            }
        }
    }
    

    private void setCell(int x, int y, int celltype) {
        map[x + xSize * y] = celltype;
    }

    //returns the type of a tile
    public int getCell(int x, int y) {
        try {
            return map[x + xSize * y];
        } catch (Exception e) {
            // Do nothing 
        }
        return 0;
    }
}
