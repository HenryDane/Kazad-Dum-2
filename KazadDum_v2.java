/*
 * This is the main part of the game; the contructor is the main loop :/
 * This was written with netbeans; the //<editor-fold . . . stuff is to compact the code. 
 */
package kazad_dum_2;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import kazad_dum_2.util.*;
import kazad_dum_2.types.standard.DungeonTile;
import kazad_dum_2.algorithims.BrownDungeonGenerator;
import kazad_dum_2.types.standard.Cube;
import kazad_dum_2.types.standard.Placeable;
import kazad_dum_2.types.standard.PlaceableType;
import static kazad_dum_2.types.standard.PlaceableType.Pyramid;
import kazad_dum_2.types.standard.Pyramid;
import kazad_dum_2.types.standard.Tile;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Henry Dane
 */
public class KazadDum_v2 {
//    boolean closeFog = false;
    int size = 32;              //size of the dungeon -- x and y
    boolean strongFog = false;  //enable super dense fog
    boolean noFog = false;      //disable fog
    boolean debug = false;      //enable debug -- does nothing
    boolean noTextures = true;  //disale textures -- runs way faster when this is true

    // <editor-fold defaultstate="collapsed" desc="Variable Definitions">
    BrownDungeonGenerator bdg2;
    GameState GAMESTATE = GameState.Initalizing;
    List<DungeonTile> adrdata;
    List<Tile> bdgtiles;
    List<Placeable> crates;
    DisplayMode DISPLAYMODE;
    private static final FloatBuffer lightPosition = BufferTools.asFlippedFloatBuffer(5, 10, 6, 1);
    private static FloatBuffer lightPosition2 = BufferTools.asFlippedFloatBuffer(5, 10, 6, 1); //unnecessary but handy to have around
    private static EulerCamera camera;
    float LOWSPEED = 1;
    float HIGHSPEED = 2;
    float speed = LOWSPEED;
    boolean crdrs = false;
    float ylock = .5f;
    float YLOCKED = .5f;
    // opengl display lists
    int dungeonDisplayList;
    int dungeonFloorList;
    int dungeonWallList;
    int dungeonCrateList;
    int dungeonRockList;
    Texture floorTexture;
    Texture wallTexture;
    Texture crateTexture;
    long oldseed;
    Vector3f lSafePosition = new Vector3f(0, 0, 0);
    Vector3f lSafeLook = new Vector3f(0, 0, 0);
    Vector3f cameraSpawn = new Vector3f(0, 0, 0);
    Vector3f goal = new Vector3f(0, 0, 0);
    // </editor-fold> 

    public KazadDum_v2() {
        // <editor-fold defaultstate="collapsed" desc="Display Setup">  
        try {
            DISPLAYMODE = new DisplayMode(800, 600);
            Display.setDisplayMode(DISPLAYMODE);
            Display.setVSyncEnabled(true);
            Display.setTitle("Kazad Dum Alpha v2");
            Display.create();
        } catch (LWJGLException e) {
            System.err.println("Couldn't set up the display");
            Display.destroy();
            System.exit(1);
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="OpenGL Setup">  
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_NORMALIZE);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_DEPTH_TEST);

        float GDValue = .5f;
        float GAValue = .3f;
        float GLMValue = 0f;

        glLightModel(GL_LIGHT_MODEL_AMBIENT, BufferTools.asFlippedFloatBuffer(GLMValue, GLMValue, GLMValue, 1));
        glEnable(GL_LIGHT0);
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_AMBIENT, BufferTools.asFlippedFloatBuffer(GAValue, GAValue, GAValue, 1));
        glLight(GL_LIGHT0, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(GDValue, GDValue, GDValue, 1));

        float GDValue2 = .5f;
        float GAValue2 = .3f;
//        float GLMValue2 = 0f;
//        glEnable(GL_LIGHT1);
        //intensity = cos (90° / (maxradius * distance))
//        glLight(GL_LIGHT1, GL_POSITION, lightPosition2);
//        glLight(GL_LIGHT1, GL_AMBIENT, BufferTools.asFlippedFloatBuffer(0, 0, GAValue2, 1));
//        glLight(GL_LIGHT1, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(0, 0, GDValue2, 1));
//        glLightf( GL_LIGHT1, GL_SPOT_CUTOFF, 64f );
//        glLightf(GL_LIGHT1, GL_SPOT_DIRECTION, 1);
//        glLightfv( GL_LIGHT1, GL_SPOT_DIRECTION, {0,-1,0} );

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glFogf(GL_FOG_START, 1f);
        glFogf(GL_FOG_END, 15.0f);
        if (!noFog) {
            glEnable(GL_FOG);
        }
        FloatBuffer fogColor = BufferUtils.createFloatBuffer(4);
        fogColor.put(1f).put(1f).put(1f).put(1f).flip();
        if (strongFog) {
            glFogi(GL_FOG_MODE, GL_EXP2);
        } else {
            glFogi(GL_FOG_MODE, GL_LINEAR);
        }
        glFog(GL_FOG_COLOR, fogColor);
        if (strongFog) {
            glFogf(GL_FOG_DENSITY, .6f);
        } else {
            glFogf(GL_FOG_DENSITY, .3f);
        }
        glHint(GL_FOG_HINT, GL_NICEST);
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Camera Setup">
        camera = new EulerCamera.Builder().setAspectRatio((float) DISPLAYMODE.getWidth() / DISPLAYMODE.getHeight()).setPosition(23, 34, 87).setRotation(0, 0,
                0).setNearClippingPane(.1f).setFarClippingPane(300).setFieldOfView(60).build();
        camera.applyOptimalStates();
        camera.applyPerspectiveMatrix();
        camera.setPosition(50, 0, 50);
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Generate map, dungeon, and display lists">
        Runnable mapRunnable = () -> {
            BrownDungeonGenerator bdg = new BrownDungeonGenerator();
            System.out.println("BDG: ===================================================================");
            bdg.createDungeon(size, size, 100000000);
            bdgtiles = bdg.exportAsTiles();
            System.out.println(bdg.showDungeon());
            bdg2 = bdg;
        };
        Thread loadingThread1 = new Thread(mapRunnable);
        loadingThread1.start();
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Set up textures">
        if (!noTextures) {
            floorTexture = loadTexture("floor1");
            wallTexture = loadTexture("wall1");
            crateTexture = loadTexture("crate");
        }
        // </editor-fold>
        // <editor-fold defaultstate="collapsed" desc="Glow Color Definitions">  
        FloatBuffer glowColorBlue = BufferUtils.createFloatBuffer(4);
        glowColorBlue.put(0f).put(0f).put(1f).put(0f).flip();
        FloatBuffer glowColorRed = BufferUtils.createFloatBuffer(4);
        glowColorRed.put(1f).put(0f).put(0f).put(0f).flip();
        FloatBuffer glowColorNone = BufferUtils.createFloatBuffer(4);
        glowColorNone.put(0f).put(0f).put(0f).put(0f).flip();
        //</editor-fold>
//        glPolygonMode (GL_FRONT_AND_BACK, GL_LINE);
        while (!Display.isCloseRequested()) {
            // <editor-fold defaultstate="collapsed" desc="OpenGl Loop Operations I">  
            glLoadIdentity();
            camera.applyTranslations();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//            glClear(GL_DEPTH_BUFFER_BIT);
            checkInput();
            //</editor-fold>

            switch (GAMESTATE) {
                case GeneratingLevel:
                    // <editor-fold defaultstate="collapsed" desc="Regenerate the level">  
                    crates.clear();
                    BrownDungeonGenerator bdg = new BrownDungeonGenerator();
                    System.out.println("BDG: ===================================================================");
                    bdg.createDungeon(size, size, 100000000);
                    bdgtiles = bdg.exportAsTiles();
                    System.out.println(bdg.showDungeon());
                    bdg2 = bdg;
                // </editor-fold>
                case Initalizing:
                    // <editor-fold defaultstate="collapsed" desc="TODO while loading">  
                    if (!loadingThread1.isAlive()) {
                        //if the loading thread is dead, make all of the lists.
                        dungeonDisplayList = glGenLists(1);
                        dungeonFloorList = glGenLists(1);
                        dungeonWallList = glGenLists(1);
                        dungeonCrateList = glGenLists(1);
                        crates = new ArrayList<>();
                        glNewList(dungeonFloorList, GL_COMPILE);
                        for (Tile tile : bdgtiles) {
                            if (tile.t != 0 && tile.t != 1 && tile.t != 3) {
                                tile.draw();
                                if (getRand(0, 20) == 1) { // 20 & 0
                                    crates.add(new Cube(.4f, tile.getX() + .5f, 0, tile.getY() + .5f));
//                                    crates.add(new Pyramid(.4f, tile.getX() - .5f, 2, tile.getY() + .5f));

                                    if (getRand(0, 5) == 1) { // 0 & 5
                                        switch (getRand(0, 3)) {
                                            case 0:
                                                crates.add(new Cube(.4f, tile.getX() + .5f, 0f, tile.getY() + .5f + .4f));
                                                break;
                                            case 1:
                                                crates.add(new Cube(.4f, tile.getX() + .5f, 0f, tile.getY() + .5f - .4f));
                                                break;
                                            case 2:
                                                crates.add(new Cube(.4f, tile.getX() + .5f + .4f, 0f, tile.getY() + .5f));
                                                break;
                                            case 3:
                                                crates.add(new Cube(.4f, tile.getX() + .5f + .4f, 0f, tile.getY() + .5f));
                                                break;
                                        }
//                                        if (getRand(0, 2) == 1) {
//                                        }

                                    }
//                                    drawCube(.4f, tile.getX() + .5f, 0, tile.getY() + .5f);
                                }
                            }
                            if (tile.t == 6) {
                                camera.setPosition((float) tile.getX() + .5f, (float) tile.getZ(), (float) tile.getY() + .5f);
                                lSafePosition = new Vector3f((float) tile.getX() + .5f, (float) tile.getZ(), (float) tile.getY() + .5f);
                                cameraSpawn = lSafePosition;
                            } else if (tile.t == 7) {
                                goal = new Vector3f((float) tile.getX() + .5f, .3f, (float) tile.getY() + .5f);
                            } else if (tile.t == 2 || tile.t == 4) {
                                float rx = tile.getX();
                                float rz = tile.getY();
//                                drawCubeBroken(.1f, rx, .5f, rz);
                            }
                        }
                        glEndList();
                        glNewList(dungeonWallList, GL_COMPILE);
                        for (Tile tile : bdgtiles) {
                            if (tile.t == 0 || tile.t == 1 || tile.t == 3) {
                                tile.draw();
                            }
                        }
                        glEndList();

                        glNewList(dungeonCrateList, GL_COMPILE);
                        for (Placeable p : crates) {
                                p.draw();
                            
                        }
                        glEndList();
                        GAMESTATE = GameState.Running;
                        System.out.println("Game now running !");

                    }
                    // </editor-fold>
                    break;
                case Running:
                    // <editor-fold defaultstate="collapsed" desc="Colission Mark 1">  
                    // a really nasty way to get collissions. Really needs some fixing
                    int tdx = (int) camera.x(); //get rid of decimal places
                    int tdy = (int) camera.z(); //get rid of decimal places
                    if (bdg2.getCell(tdx, tdy) != 0 && bdg2.getCell(tdx, tdy) != 1 && bdg2.getCell(tdx, tdy) != 3) { //check if the cell of the map is safe
                        lSafePosition = new Vector3f(camera.x(), camera.y(), camera.z()); //if safe save location
                    } else { // if the cell isn't safe
                        Vector3f thisTileRepulsion = getRepulsion(new Vector3f(camera.x(), camera.y(), camera.z()), new Vector3f(tdx + .5f, 0, tdy + .5f), .01f); //calculate the "repulsion" factor
                        lSafePosition = new Vector3f(lSafePosition.x + thisTileRepulsion.x, lSafePosition.y + thisTileRepulsion.y, lSafePosition.z + thisTileRepulsion.z); //recalculate the last safe position
                        camera.setPosition(lSafePosition.x, lSafePosition.y, lSafePosition.z); // move the camera to the last safe position
                        lSafePosition = new Vector3f(camera.x(), camera.y(), camera.z()); // set the last safe position to the camera's position
                    }

                    //Next Level
                    if ((goal.x) < camera.x() && (goal.x + .4f) > camera.x()) {
                        if ((goal.z) < camera.z() && (goal.z + .4f) > camera.z()) { // is the camera in the level complete cube?
                            System.out.println("Camera @ X:" + camera.x() + " Y:" + camera.y() +" Z:" + camera.z());
                            System.out.println("Goal @ X:" + goal.x +" Y:" + goal.y + " Z:" + goal.z);
                            GAMESTATE = GameState.GeneratingLevel;
                        }
                    }

                    //</editor-fold>
                    // <editor-fold defaultstate="collapsed" desc="Draw Everything">  
                    if (!noTextures) {
                        floorTexture.bind();
                    }
                    glCallList(dungeonFloorList);

                    if (!noTextures) {
                        wallTexture.bind();
                    }
                    glCallList(dungeonWallList);

                    if (!noTextures) {
                        crateTexture.bind();
                    }
                    for (Placeable p : crates) {
                            p.draw();
                    }
                    
                    // enable glowyness
                    glDepthMask(false);
                    glCullFace(GL_FRONT);
                    glEnable(GL_BLEND);
                    glBlendFunc(GL_SRC_ALPHA, GL_ONE);

                    // draw starting pos cube
                    glMaterial(GL_FRONT, GL_EMISSION, glowColorBlue);
                    glColor4f(0, 0, 1f, .6f);
                    drawCube(.3f, cameraSpawn.x, cameraSpawn.y + .5f - .2f, cameraSpawn.z);
                    Pyramid py = new Pyramid(1f, cameraSpawn.x, cameraSpawn.y, cameraSpawn.z); 
                    py.draw(); //doesn't work. Why? no one knows.
                    
                    glMaterial(GL_FRONT, GL_EMISSION, glowColorRed);
                    glColor4f(1f, 0, 0, .6f);
                    drawCube(.3f, goal.x, goal.y, goal.z);

//                    glPointSize(10f);
//                    glBegin(GL_POINTS);
//                    for(int i = 0; i < 20; i++){
//                        glVertex3f((float) (cameraSpawn.x + (Math.random() / 10) + .4f), (float) (cameraSpawn.y + Math.random()), (float) (cameraSpawn.z + (Math.random() / 10)));
//                    }
//                    glEnd();
                    
                    glMaterial(GL_FRONT, GL_EMISSION, glowColorNone);
                    glDisable(GL_BLEND);
                    glDisable(GL_CULL_FACE);
                    glDepthMask(true);
                    glColor4f(1, 1, 1, 1f);
                //</editor-fold>
            }

            // <editor-fold defaultstate="collapsed" desc="OpenGl Loop Operations II">  
            Display.update();
            Display.sync(60);
            // </editor-fold>
        }
        System.out.println("Game over!");
    }

    private void checkInput() {
        if (ylock == YLOCKED) {
            camera.processKeyboard(16, speed, speed, speed, ylock);
        } else {
            camera.processKeyboard(16, speed, speed, speed);
        }
        camera.processMouse(1, 80, -80);
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            System.out.println("Game over!");
            Display.destroy();
            System.exit(0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            if (speed == LOWSPEED) {
                speed = HIGHSPEED;
                ylock = 0;
            } else {
                speed = LOWSPEED;
                ylock = .5f;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            System.out.println("Camera is at: (X)" + camera.x() + " (Y)" + camera.y() + " (Z)" + camera.z());
        } else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            if (speed == LOWSPEED) {
                speed = HIGHSPEED;
            } else {
                speed = LOWSPEED;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
            crates.add(new Cube(.4f, camera.x(), 0f, camera.z()));
            lightPosition2 = BufferTools.asFlippedFloatBuffer(camera.x(), .2f, camera.z(), 1);
//            glEnable(GL_LIGHT1);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            // y - y1 = m(x - x1)

            System.out.println("Break Crate");
        } else if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            System.out.println("REGEN");
            GAMESTATE = GameState.GeneratingLevel;
        }
        if (Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        } else if (Mouse.isButtonDown(1)) {
            Mouse.setGrabbed(false);
        }
    }

    private Texture loadTexture(String key) {
        Texture texture = null;
        try {
//            texture = TextureLoader.getTexture("PNG", new FileInputStream("src/kazad_dum_2/res/" + key + ".png"));
            texture = TextureLoader.getTexture("PNG", ClassLoader.getSystemResourceAsStream("kazad_dum_2/res/" + key + ".png"));
            return texture;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        KazadDum_v2 kd = new KazadDum_v2();
//        float data = 1.1f;
//        System.out.println(data);
////        int data2 = (int) data;
//        ++data;
//        System.out.println(data);
    }

    private int getRand(int min, int max) {

        //the seed is based on current date and the old, already used seed
        Date now = new Date();
        long seed = now.getTime() + oldseed;
        oldseed = seed;

        Random randomizer = new Random(seed);
        int n = max - min + 1;
        int i = randomizer.nextInt(n);
        if (i < 0) {
            i = -i;
        }

        //System.out.println("seed: " + seed + "\tnum:  " + (min + i));
        return min + i;
    }

    private Vector3f getRepulsion(Vector3f collider, Vector3f collided, float minrepulsionforce) {
//        float minrepulsionforce = .05f;
        Vector3f repulsion = new Vector3f(0, 0, 0);
        if (collider.x < collided.x) {
            repulsion.x -= minrepulsionforce;
//            repulsion.x -= collided.x - collider.x;
        } else if (collider.x > collided.x) {
            repulsion.x += minrepulsionforce;
//            repulsion.x += collider.x - collided.x;
        } else if (collider.x == collided.x) {
            repulsion.x = 0f;
        }

        if (collider.z < collided.z) {
            repulsion.z -= minrepulsionforce;
//            repulsion.z -= collided.z - collider.z;
        } else if (collider.z > collided.z) {
            repulsion.z += minrepulsionforce;
//            repulsion.z += collider.z - collided.z;
        } else if (collider.z == collided.z) {
            repulsion.z = 0f;
        }

        return repulsion;
    }

    private void drawCube(float s, float x, float y, float z) {

        glPushMatrix();
        glTranslatef(x, y, z);
        glScalef(s, s, s);

        glBegin(GL_QUADS);
//      Top Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0, 1, 0);
        glTexCoord2f(1, 0);
        glVertex3f(1, 1, 0);
        glTexCoord2f(1, 1);
        glVertex3f(1, 1, 1);
        glTexCoord2f(0, 1);
        glVertex3f(0, 1, 1);

//      Bottom Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1, 0);
        glVertex3f(1, 0, 0);
        glTexCoord2f(1, 1);
        glVertex3f(1, 0, 1);
        glTexCoord2f(0, 1);
        glVertex3f(0, 0, 1);

//      Front Face
        glNormal3f(0.0f, 0.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0, 0, 1.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f, 0, 1.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0, 1.0f, 1.0f);
        // Back Face
        glNormal3f(0.0f, 0.0f, -1.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        // Right face
        glNormal3f(1.0f, 0.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 1.0f);
        // Left Face
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 1.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glEnd();

        glPopMatrix();

    }

    private void drawCubeBroken(float s, float x, float y, float z) {

        glPushMatrix();
        glTranslatef(x + ((float) Math.random()), y, z + ((float) Math.random()));
        glScalef(s, s, s);

        glBegin(GL_QUADS);
//      Top Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0 + ((float) Math.random()), 1, 0 + ((float) Math.random()));
        glTexCoord2f(1, 0);
        glVertex3f(1 + ((float) Math.random()), 1, 0 + ((float) Math.random()));
        glTexCoord2f(1, 1);
        glVertex3f(1 + ((float) Math.random()), 1, 1 + ((float) Math.random()));
        glTexCoord2f(0, 1);
        glVertex3f(0 + ((float) Math.random()), 1, 1 + ((float) Math.random()));

//      Bottom Face
        glNormal3f(0, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(0 + ((float) Math.random()), 0, 0 + ((float) Math.random()));
        glTexCoord2f(1, 0);
        glVertex3f(1 + ((float) Math.random()), 0, 0 + ((float) Math.random()));
        glTexCoord2f(1, 1);
        glVertex3f(1 + ((float) Math.random()), 0, 1 + ((float) Math.random()));
        glTexCoord2f(0, 1);
        glVertex3f(0 + ((float) Math.random()), 0, 1 + ((float) Math.random()));

//      Front Face
        glNormal3f(0.0f, 0.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0 + ((float) Math.random()), 0, 1.0f + ((float) Math.random()));
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f + ((float) Math.random()), 0, 1.0f + ((float) Math.random()));
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f + ((float) Math.random()), 1.0f, 1.0f + ((float) Math.random()));
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0 + ((float) Math.random()), 1.0f, 1.0f + ((float) Math.random()));
        // Back Face
        glNormal3f(0.0f, 0.0f, -1.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        // Right face
        glNormal3f(1.0f, 0.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(1.0f, 0, 0);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 0);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(1.0f, 0, 1.0f);
        // Left Face
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glTexCoord2f(0.0f, 0.0f);
        glVertex3f(0, 0, 0);
        glTexCoord2f(1.0f, 0.0f);
        glVertex3f(0, 0, 1.0f);
        glTexCoord2f(1.0f, 1.0f);
        glVertex3f(0, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 1.0f);
        glVertex3f(0, 1.0f, 0);
        glEnd();

        glPopMatrix();

    }
}
