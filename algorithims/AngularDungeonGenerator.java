/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kazad_dum_2.algorithims;

import java.awt.geom.Rectangle2D;
import kazad_dum_2.types.angular.AngularDungeonRoom;
import kazad_dum_2.types.angular.AngularDungeonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import kazad_dum_2.types.angular.AngularDungeonCorridor;
import kazad_dum_2.util.WeightedQuickUnionUF;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Henry Dane
 */
public class AngularDungeonGenerator {

    List<AngularDungeonObject> dungeonObjects = new ArrayList<>();
    List<Vector3f> unconnectedPoints = new ArrayList<>();
    int numObjects;

    public AngularDungeonGenerator(int numObjects) {
        this.numObjects = numObjects;
    }

    public void generateBasic() {
        for (int i = 0; i < numObjects; i++) {
            dungeonObjects.add(new AngularDungeonRoom(randInt(0, numObjects), randInt(0, numObjects), randInt(0, numObjects), 0, 0, 0, randInt(3, 7), randInt(3, 7), 1, i));
        }
    }

    public void unlump() {
        int scnt = 0;
        while (true) {
            scnt = 0;
            WeightedQuickUnionUF wquuf = new WeightedQuickUnionUF(dungeonObjects.size());
            for (AngularDungeonObject adg1 : dungeonObjects) {
                if (!adg1.isRoom()) {
                    continue;
                }
                for (AngularDungeonObject adg2 : dungeonObjects) {
                    if (!wquuf.connected(adg1.getUUID(), adg2.getUUID())) {
                        wquuf.union(adg1.getUUID(), adg2.getUUID());
                    } else {
                        continue;
                    }
                    Rectangle2D a = adg1.getRect();
                    if (a == null) {
                        System.out.println("A");
                    }
                    Rectangle2D b = adg2.getRect();
                    if (b == null) {
                        System.out.println("B");
                    }
                    if (a.intersects(b) == true) {
                        Rectangle2D r2d1 = (Rectangle2D) adg1.getRect();
                        Rectangle2D r2d2 = (Rectangle2D) adg2.getRect();
                        double xdf = r2d1.getCenterX() - r2d2.getCenterX();
                        double xdy = r2d1.getCenterY() - r2d2.getCenterY();
                        adg1.setX((float) (adg1.getX() + xdf));
                        adg1.setY((float) (adg1.getY() + xdy));
                    } else {
                        scnt++;
                    }
                }
            }
            if (scnt >= dungeonObjects.size() - (dungeonObjects.size() / 6)) {
                break;
            }
        }
    }

    public void generateCorridors() {
        WeightedQuickUnionUF wquuf = new WeightedQuickUnionUF(dungeonObjects.size());
        for (int i = 0; i < dungeonObjects.size(); i++) {
            for (int j = 0; j < randInt(1,4); j++) {
                if (i != j) {
                    AngularDungeonObject adg1 = dungeonObjects.get(i);
                    AngularDungeonObject adg2 = dungeonObjects.get(randInt(0,dungeonObjects.size()-1));
                    if (adg1.isRoom() == true && adg2.isRoom() == true) {
                        if (!wquuf.connected(adg1.getUUID(), adg2.getUUID())) {
                            wquuf.union(adg1.getUUID(), adg2.getUUID());
                        } else {
                            continue;
                        }
                        dungeonObjects.add(new AngularDungeonCorridor(adg1.getX(), adg1.getY(), adg1.getZ(), adg2.getX(), adg2.getY(), adg2.getZ(), 0, 0, 0, 0));
                    }
                }
            }
        }
    }

    public List<AngularDungeonObject> export() {
        return dungeonObjects;
    }

    private static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
