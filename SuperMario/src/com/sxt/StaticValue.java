package com.sxt;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticValue {
    //set background
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;

    //Mario jump left/right
    public static BufferedImage jumpLeft = null;
    public static BufferedImage jumpRight = null;
    //Mario stand left/right
    public static BufferedImage standLeft = null;
    public static BufferedImage standRight = null;

    //background tower
    public static BufferedImage tower = null;
    //background flag pole
    public static BufferedImage flagPole = null;

    //set  Piranha Plant
    //set obstacles
    public static List<BufferedImage> obstacle = new ArrayList<>();
    // set list of run images with jump and stand
    public static List<BufferedImage> runLeft = new ArrayList<>();
    public static List<BufferedImage> runRight = new ArrayList<>();
    //set mushroom images with size big and small
    public static List<BufferedImage> mushroom = new ArrayList<>();
    //set plant images with mouth open and shut
    public static List<BufferedImage> plant = new ArrayList<>();
    //set path to store a directory to locate images by retrieving the current
    //working directory and appending /src/images/
    public static String path = System.getProperty("user.dir") + "/src/images/";

    //initialize images using ImageIO(ImageIO is a class in Java that read and write images)
    public static void init() {
        try {
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            standLeft = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            standRight = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            tower = ImageIO.read(new File(path + "tower.png"));
            flagPole = ImageIO.read(new File(path + "gan.png"));
            jumpLeft = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            jumpRight = ImageIO.read(new File(path + "s_mario_jump1_R.png"));

        }catch (IOException e){
            e.printStackTrace();
        }

        // for loop mario run to left pictures

        for(int i=1;i<=2;i++){
            try{
                runLeft.add(ImageIO.read(new File(path + "s_mario_run"+i+"_L.png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // for loop mario run to right pictures
        for(int i=1;i<=2;i++){
            try{
                runRight.add(ImageIO.read(new File(path + "s_mario_run"+i+"_R.png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //load obstacles
        try{
            obstacle.add(ImageIO.read(new File(path + "brick.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));

        }catch (IOException e){
            e.printStackTrace();
        }

        //load pipes
        for(int i=1;i<=4;i++){
            try{
                obstacle.add(ImageIO.read(new File(path + "pipe"+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //load blue bricks and flag
        try{
            obstacle.add(ImageIO.read(new File(path + "brick2.png" )));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        }catch (IOException e){
            e.printStackTrace();
        }

        //load mushrooms
        for(int i=1;i<=3;i++){
            try{
                mushroom.add(ImageIO.read(new File(path + "fungus"+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //load plant
        for(int i=1;i<=2;i++){
            try{
                plant.add(ImageIO.read(new File(path + "flower1."+i+".png")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
