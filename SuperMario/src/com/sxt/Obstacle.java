package com.sxt;

import java.awt.image.BufferedImage;

public class Obstacle implements Runnable{
    // (x,y) mark obstacle position
    private int x;
    private int y;
    //obstacle type(related to add order)
    private int type;
    //show obstacle image
    private BufferedImage showObstacle = null;
    //current background
    private Background bg = null;
    //declare thread for flag move
    private Thread thread = new Thread(this);

    public Obstacle(int x,int y,int type,Background bg){
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;

        showObstacle = StaticValue.obstacle.get(type);

        //if type = 8(flag image type )then flag starts thread
        if(type == 8){
            thread.start();
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }

    public BufferedImage getShowObstacle() {
        return showObstacle;
    }

    public Background getBg() {
        return bg;
    }

    @Override
    public void run() {
    while(true){
        if(this.bg.doesReachFlagPole()){
            if(this.y < 374){
                this.y += 5;
            }else {
               this.bg.setDoesReachFlagPole(true);
            }
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
    }
    }
}
