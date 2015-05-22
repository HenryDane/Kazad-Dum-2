package kazad_dum_2;

import java.nio.FloatBuffer;
import java.util.List;
import kazad_dum_2.util.*;
import kazad_dum_2.algorithims.AngularDungeonGenerator;
import kazad_dum_2.types.angular.AngularDungeonObject;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Henry Dane
 */
public class KazadDum {
    List<AngularDungeonObject> adrdata;
    DisplayMode DISPLAYMODE;
    private static FloatBuffer lightPosition = BufferTools.asFlippedFloatBuffer(5, 10, 6, 1);
    private static EulerCamera camera;
    float speed = 256;
    boolean crdrs = false;
    
    public KazadDum() {
        setupdisplay();
        setupstates();
        setupcamera();
        generatemap();
        
        int i = 0;
        while (!Display.isCloseRequested()) {
            glLoadIdentity();
            camera.applyTranslations();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            checkInput();
            
            glPushMatrix();
            glTranslatef(0,0,0);
            glColor3f(1, 1, 1);
            for(AngularDungeonObject ado : adrdata){
                ado.draw();
            }
            glPopMatrix();
            if((i % 60 )== 0){
                i = 0;
                generatemap();
            }
            i++;
            Display.update();
            Display.sync(60);
        }
    }

    private void checkInput() {
        camera.processKeyboard(2, speed);
        camera.processMouse(1, 80, -80);
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Display.destroy();
            System.exit(0);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
            if (speed == 256){
                speed = 512;
            } else {
                speed = 256;
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_E)){
            if(crdrs){
                crdrs = false;
            } else {
                crdrs=true;
            }
        }
        if (Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        } else if (Mouse.isButtonDown(1)) {
            Mouse.setGrabbed(false);
        }
    }

//    public static void main(String[] args) {
//        KazadDum kd = new KazadDum();
//    }
    
    private void setupdisplay(){
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
    }
    private void setupstates(){
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);
        glEnable(GL_NORMALIZE);
        glEnable(GL_COLOR_MATERIAL);
        glEnable(GL_DEPTH_TEST);
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLightModel(GL_LIGHT_MODEL_AMBIENT, BufferTools.asFlippedFloatBuffer(0, 0, 0, 1));
        glLight(GL_LIGHT0, GL_AMBIENT, BufferTools.asFlippedFloatBuffer(.5f, .5f, .5f, 1));
        glLight(GL_LIGHT0, GL_DIFFUSE, BufferTools.asFlippedFloatBuffer(.7f, .7f, .7f, 1));
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    } 
    private void setupcamera(){
        camera = new EulerCamera.Builder().setAspectRatio((float) DISPLAYMODE.getWidth() / DISPLAYMODE.getHeight()).setPosition(23, 34, 87).setRotation(22, 341,
            0).setNearClippingPane(2).setFarClippingPane(300).setFieldOfView(60).build();
        camera.applyOptimalStates();
        camera.applyPerspectiveMatrix();
        camera.setPosition(125, 125, 125);
    }
    private void generatemap(){
        AngularDungeonGenerator adg = new AngularDungeonGenerator(100);
        adg.generateBasic();
        adg.unlump();
        if(crdrs){
            adg.generateCorridors();
        }
        adrdata = adg.export();
        System.out.println(adrdata.size());
    }
}
