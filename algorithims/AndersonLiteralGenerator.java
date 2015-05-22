/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.algorithims;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import kazad_dum_2.types.standard.DungeonTile;
import kazad_dum_2.types.standard.Terrain;
import org.lwjgl.util.vector.Vector4f;

/**
 *
 * @author henryjmo
 */
public class AndersonLiteralGenerator {

    int xmax;
    int ymax;

    int xsize;
    int ysize;

    int objects;

    int dungeon_objects;
    int chanceRoom;
    int chanceCorridor;

    DungeonTile[] dungeon_map;

    long oldseed;

    String msgXSize;
    String msgYSize;
    String msgMaxObjects;
    String msgNumObjects;
    String msgHelp;
    String msgDetailedHelp;

    Random r = new Random();

    private void setCell(int x, int y, int celltype) {
        if (dungeon_map[x + xsize * y] == null){
            dungeon_map[x + xsize * y] = new DungeonTile(x, y, 1, 1, celltype);
        } else {
            dungeon_map[x + xsize * y].type = celltype;
        }
    }

    private int getCell(int x, int y) {
        try {
        if (dungeon_map[x + xsize * y] == null){
            dungeon_map[x + xsize * y] = new DungeonTile(x, y, 1, 1, 0);
        }
        return dungeon_map[x + xsize * y].type;
        } catch (ArrayIndexOutOfBoundsException e ){
            return 0;
        }
    }

    private int getRand(int min, int max) {
        return r.nextInt((max - min) + 1) + min;
    }

    private boolean makeCorridor(int x, int y, int lenght, int direction) {
        int len = getRand(2, lenght);
        int floor = Terrain.tileCorridor.toInt();
        int dir = 0;
        if (direction > 0 && direction < 4) {
            dir = direction;
        }

        int xtemp = 0;
        int ytemp = 0;

        switch (dir) {
            case 0: {
                if (x < 0 || x > xsize) {
                    return false;
                } else {
                    xtemp = x;
                }

                for (ytemp = y; ytemp > (y - len); ytemp--) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                        return false;
                    }
                }

                for (ytemp = y; ytemp > (y - len); ytemp--) {
                    setCell(xtemp, ytemp, floor);
                }
                break;

            }
            case 1: {
                if (y < 0 || y > ysize) {
                    return false;
                } else {
                    ytemp = y;
                }

                for (xtemp = x; xtemp < (x + len); xtemp++) {
                    if (xtemp < 0 || xtemp > xsize) {
                        return false;
                    }
                    if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                        return false;
                    }
                }

                for (xtemp = x; xtemp < (x + len); xtemp++) {
                    setCell(xtemp, ytemp, floor);
                }
                break;
            }
            case 2: {
                if (x < 0 || x > xsize) {
                    return false;
                } else {
                    xtemp = x;
                }

                for (ytemp = y; ytemp < (y + len); ytemp++) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                        return false;
                    }
                }
                for (ytemp = y; ytemp < (y + len); ytemp++) {
                    setCell(xtemp, ytemp, floor);
                }
                break;
            }
            case 3: {
                if (ytemp < 0 || ytemp > ysize) {
                    return false;
                } else {
                    ytemp = y;
                }

                for (xtemp = x; xtemp > (x - len); xtemp--) {
                    if (xtemp < 0 || xtemp > xsize) {
                        return false;
                    }
                    if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                        return false;
                    }
                }

                for (xtemp = x; xtemp > (x - len); xtemp--) {
                    setCell(xtemp, ytemp, floor);
                }
                break;
            }
        }
        //woot, we're still here! let's tell the other guys we're done!!
        return true;
    }

    private boolean makeRoom(int x, int y, int xlength, int ylength, int direction) {
        //define the dimensions of the room, it should be at least 4x4 tiles (2x2 for walking on, the rest is walls)
        int xlen = getRand(4, xlength);
        int ylen = getRand(4, ylength);
        //the tile type it's going to be filled with
        int floor = Terrain.tileDirtFloor.toInt(); //jordgolv..
        int wall = Terrain.tileDirtWall.toInt(); //jordv????gg
        //choose the way it's pointing at
        int dir = 0;
        if (direction > 0 && direction < 4) {
            dir = direction;
        }

        switch (dir) {
            case 0:
                //north
                //Check if there's enough space left for it
                for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
                        if (xtemp < 0 || xtemp > xsize) {
                            return false;
                        }
                        if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                            return false; //no space left...
                        }
                    }
                }

                //we're still here, build
                for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
                    for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
                        //start with the walls
                        if (xtemp == (x - xlen / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (xtemp == (x + (xlen - 1) / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == y) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y - ylen + 1)) {
                            setCell(xtemp, ytemp, wall);
                        } //and then fill with the floor
                        else {
                            setCell(xtemp, ytemp, floor);
                        }
                    }
                }
                break;
            case 1:
                //east
                for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    for (int xtemp = x; xtemp < (x + xlen); xtemp++) {
                        if (xtemp < 0 || xtemp > xsize) {
                            return false;
                        }
                        if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                            return false;
                        }
                    }
                }

                for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
                    for (int xtemp = x; xtemp < (x + xlen); xtemp++) {

                        if (xtemp == x) {
                            setCell(xtemp, ytemp, wall);
                        } else if (xtemp == (x + xlen - 1)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y - ylen / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y + (ylen - 1) / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else {
                            setCell(xtemp, ytemp, floor);
                        }
                    }
                }
                break;
            case 2:
                //south
                for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
                        if (xtemp < 0 || xtemp > xsize) {
                            return false;
                        }
                        if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                            return false;
                        }
                    }
                }

                for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
                    for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {

                        if (xtemp == (x - xlen / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (xtemp == (x + (xlen - 1) / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == y) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y + ylen - 1)) {
                            setCell(xtemp, ytemp, wall);
                        } else {
                            setCell(xtemp, ytemp, floor);
                        }
                    }
                }
                break;
            case 3:
                //west
                for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
                    if (ytemp < 0 || ytemp > ysize) {
                        return false;
                    }
                    for (int xtemp = x; xtemp > (x - xlen); xtemp--) {
                        if (xtemp < 0 || xtemp > xsize) {
                            return false;
                        }
                        if (getCell(xtemp, ytemp) != Terrain.tileUnused.toInt()) {
                            return false;
                        }
                    }
                }

                for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
                    for (int xtemp = x; xtemp > (x - xlen); xtemp--) {

                        if (xtemp == x) {
                            setCell(xtemp, ytemp, wall);
                        } else if (xtemp == (x - xlen + 1)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y - ylen / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else if (ytemp == (y + (ylen - 1) / 2)) {
                            setCell(xtemp, ytemp, wall);
                        } else {
                            setCell(xtemp, ytemp, floor);
                        }
                    }
                }
                break;
        }

        //yay, all done
        return true;
    }

    public String showDungeon() {
        String s = "";
        int tileUnused = 0;

        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                //System.out.print(getCell(x, y));
                switch (getCell(x, y)) {
                    case 0:
                        System.out.print(" ");
                        break;
                    case 1:
                        System.out.print("#");
                        break;
                    case 2:
                        System.out.print(".");
                        break;
                    case 3:
                        System.out.print("X");
                        break;
                    case 4:
                        System.out.print(".");
                        break;
                    case 5:
                        System.out.print("+");
                        break;
                    case 6:
                        System.out.print("<");
                        break;
                    case 7:
                        System.out.print(">");
                        break;
                    case 8:
                        System.out.print("*");
                        break;
                };
            }
            System.out.print("\n");
            //if (xsize <= xmax) printf("\n");
        }

        return s;
    }

    public boolean createDungeon(int inx, int iny, int inobj) {
        if (inobj < 1) {
            objects = 10;
        } else {
            objects = inobj;
        }

        //justera kartans storlek, om den ????r st????rre eller mindre ????n "gr????nserna"
        //adjust the size of the map, if it's smaller or bigger than the limits
        if (inx < 3) {
            xsize = 3;
        } else if (inx > xmax) {
            xsize = xmax;
        } else {
            xsize = inx;
        }

        if (iny < 3) {
            ysize = 3;
        } else if (iny > ymax) {
            ysize = ymax;
        } else {
            ysize = iny;
        }

        //printf("%s %d\n", msgXSize.c_str(), xsize);
        //printf("%s %d\n", msgYSize.c_str(),  + ysize);
        //printf("%s %d\n", msgMaxObjects.c_str(), objects);
        //redefine the map var, so it's adjusted to our new map size
        dungeon_map = new DungeonTile[xsize * ysize];
        System.out.println(xsize);
        System.out.println(ysize);
        System.out.println();
        //start with making the "standard stuff" on the map
        for (int y = 0; y < ysize; y++) {
            for (int x = 0; x < xsize; x++) {
                //ie, making the borders of unwalkable walls
                if (y == 0) {
                    setCell(x, y, Terrain.tileStoneWall.toInt());
                } else if (y == ysize - 1) {
                    setCell(x, y, Terrain.tileStoneWall.toInt());
                } else if (x == 0) {
                    setCell(x, y, Terrain.tileStoneWall.toInt());
                } else if (x == xsize - 1) {
                    setCell(x, y, Terrain.tileStoneWall.toInt());
                } //and fill the rest with dirt
                else {
                    setCell(x, y, Terrain.tileUnused.toInt());
                }
            }
        }

        /**
         * *****************************************************************************
         * And now the code of the random-map-generation-algorithm begins!
         * *****************************************************************************
         */
        //start with making a room in the middle, which we can start building upon
        makeRoom(xsize / 2, ysize / 2, 8, 6, getRand(0, 3)); //getrand saken f????r att slumpa fram riktning p?? rummet

        //keep count of the number of "objects" we've made
        int currentFeatures = 1; //+1 for the first room we just made

        //then we sart the main loop
        for (int countingTries = 0; countingTries < dungeon_objects; countingTries++) {
            //check if we've reached our quota
            if (currentFeatures == objects) {
                break;
            }

            //start with a random wall
            int newx = 0;
            int xmod = 0;
            int newy = 0;
            int ymod = 0;
            int validTile = -1;
            //1000 chances to find a suitable object (room or corridor)..
            //(yea, i know it's kinda ugly with a for-loop... -_-')
            for (int testing = 0; testing < 1000; testing++) {
                newx = getRand(1, xsize - 1);
                newy = getRand(1, ysize - 1);
                validTile = -1;
                //System.out.println("tempx: " + newx + "\ttempy: " + newy);
                if (getCell(newx, newy) == Terrain.tileDirtWall.toInt() || getCell(newx, newy) == Terrain.tileCorridor.toInt()) {
                    //check if we can reach the place
                    if (getCell(newx, newy + 1) == Terrain.tileDirtWall.toInt() || getCell(newx, newy + 1) == Terrain.tileCorridor.toInt()) {
                        validTile = 0; //
                        xmod = 0;
                        ymod = -1;
                    } else if (getCell(newx - 1, newy) == Terrain.tileDirtWall.toInt() || getCell(newx - 1, newy) == Terrain.tileCorridor.toInt()) {
                        validTile = 1; //
                        xmod = +1;
                        ymod = 0;
                    } else if (getCell(newx, newy - 1) == Terrain.tileDirtWall.toInt() || getCell(newx, newy - 1) == Terrain.tileCorridor.toInt()) {
                        validTile = 2; //
                        xmod = 0;
                        ymod = +1;
                    } else if (getCell(newx + 1, newy) == Terrain.tileDirtWall.toInt() || getCell(newx + 1, newy) == Terrain.tileCorridor.toInt()) {
                        validTile = 3; //
                        xmod = -1;
                        ymod = 0;
                    }

                    //check that we haven't got another door nearby, so we won't get alot of openings besides
                    //each other
                    if (validTile > -1) {
                        if (getCell(newx, newy + 1) == Terrain.tileDoor.toInt()) //north
                        {
                            validTile = -1;
                        } else if (getCell(newx - 1, newy) == Terrain.tileDoor.toInt())//east
                        {
                            validTile = -1;
                        } else if (getCell(newx, newy - 1) == Terrain.tileDoor.toInt())//south
                        {
                            validTile = -1;
                        } else if (getCell(newx + 1, newy) == Terrain.tileDoor.toInt())//west
                        {
                            validTile = -1;
                        }
                    }

                    //if we can, jump out of the loop and continue with the rest
                    if (validTile > -1) {
                        break;
                    }
                }
            }
            if (validTile > -1) {
                //choose what to build now at our newly found place, and at what direction
                int feature = getRand(0, 100);
                if (feature <= chanceRoom) { //a new room
                    if (makeRoom((newx + xmod), (newy + ymod), 8, 6, validTile)) {
                        currentFeatures++; //add to our quota

                        //then we mark the wall opening with a door
                        setCell(newx, newy, Terrain.tileDoor.toInt());

                        //clean up infront of the door so we can reach it
                        setCell((newx + xmod), (newy + ymod), Terrain.tileDirtFloor.toInt());
                    }
                } else if (feature >= chanceRoom) { //new corridor
                    if (makeCorridor((newx + xmod), (newy + ymod), 6, validTile)) {
                        //same thing here, add to the quota and a door
                        currentFeatures++;

                        setCell(newx, newy, Terrain.tileDoor.toInt());
                    }
                }
            }
        }

        /**
         * *****************************************************************************
         * All done with the building, let's finish this one off
         * *****************************************************************************
         */
        //sprinkle out the bonusstuff (stairs, chests etc.) over the map
        int newx = 0;
        int newy = 0;
        int ways = 0; //from how many directions we can reach the random spot from
        int state = 0; //the state the loop is in, start with the stairs
        while (state != 10) {
            for (int testing = 0; testing < 1000; testing++) {
                newx = getRand(1, xsize - 1);
                newy = getRand(1, ysize - 2); //cheap bugfix, pulls down newy to 0<y<24, from 0<y<25

                //System.out.println("x: " + newx + "\ty: " + newy);
                ways = 4; //the lower the better

                //check if we can reach the spot
                if (getCell(newx, newy + 1) == Terrain.tileDirtFloor.toInt() || getCell(newx, newy + 1) == Terrain.tileCorridor.toInt()) {
                    //north
                    if (getCell(newx, newy + 1) != Terrain.tileDoor.toInt()) {
                        ways--;
                    }
                }
                if (getCell(newx - 1, newy) == Terrain.tileDirtFloor.toInt() || getCell(newx - 1, newy) == Terrain.tileCorridor.toInt()) {
                    //east
                    if (getCell(newx - 1, newy) != Terrain.tileDoor.toInt()) {
                        ways--;
                    }
                }
                if (getCell(newx, newy - 1) == Terrain.tileDirtFloor.toInt() || getCell(newx, newy - 1) == Terrain.tileCorridor.toInt()) {
                    //south
                    if (getCell(newx, newy - 1) != Terrain.tileDoor.toInt()) {
                        ways--;
                    }
                }
                if (getCell(newx + 1, newy) == Terrain.tileDirtFloor.toInt() || getCell(newx + 1, newy) == Terrain.tileCorridor.toInt()) {
                    //west
                    if (getCell(newx + 1, newy) != Terrain.tileDoor.toInt()) {
                        ways--;
                    }
                }

                if (state == 0) {
                    if (ways == 0) {
                        //we're in state 0, let's place a "upstairs" thing
                        setCell(newx, newy, Terrain.tileUpStairs.toInt());
                        state = 1;
                        break;
                    }
                } else if (state == 1) {
                    if (ways == 0) {
                        //state 1, place a "downstairs"
                        setCell(newx, newy, Terrain.tileDownStairs.toInt());
                        state = 10;
                        break;
                    }
                }
            }
        }

        //all done with the map generation, tell the user about it and finish
        //printf("%s %d\n",msgNumObjects.c_str(), currentFeatures);
        return true;
    }

    public AndersonLiteralGenerator(int x, int y, int dobjs) {
        xmax = x;
        ymax = y;

        xsize = 0;
        ysize = 0;

        objects = 0;

        chanceRoom = 75;
        chanceCorridor = 25;

        msgXSize = "X size of dungeon: \t";
        msgYSize = "Y size of dungeon: \t";
        msgMaxObjects = "max # of objects: \t";
        msgNumObjects = "# of objects made: \t";
        msgHelp = "";
        msgDetailedHelp = "";

        dungeon_objects = dobjs;
        //dungeon_map = new int[x * y];

    }

    public List<Vector4f> exportDungeonAsPoints() {
        List<Vector4f> pointlist = new ArrayList<Vector4f>();
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                if (getCell(i, j) != 1 && getCell(i, j) != 3) {
                    pointlist.add(new Vector4f(i, j, 1, 1));
                }

            }
        }
        return pointlist;
    }

    public List<DungeonTile> exportDungeonAsTiles() {
        List<DungeonTile> tilelist = new ArrayList<DungeonTile>();
        System.out.println("XSIZE = " + xsize);
        System.out.println("YSIZE = " + ysize);
        for (int i = 0; i < xsize; i++) {
            for (int j = 0; j < ysize; j++) {
                int c = getCell(i, j);
                if (c != 1 && c != 3) {
                    tilelist.add(new DungeonTile(i, j, 1, 1, false,c));
                } else  {
                    tilelist.add(new DungeonTile(i, j, 1, 1, true,c));

                } 
                System.out.println("Y-done- = " + j + ", X-done- = " + i );
            }
        }
//        List<DungeonTile> tdat = Arrays.asList(dungeon_map);
//        return tdat;
        return tilelist;
    }

}
