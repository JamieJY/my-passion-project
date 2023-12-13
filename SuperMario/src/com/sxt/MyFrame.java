package com.sxt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener,Runnable {

    // list of all the background and current background
    private List<Background> allBg = new ArrayList<>();
    private Background currentBg = new Background();
    //this variable is used to store an off-screen image
    // that can be drawn to the screen later, which can help
    // improve performance in certain situations or
    // enable double buffering for smoother graphics rendering
    private Image offScreenImage = null;

    private Mario mario = new Mario();

    //thread to make mario move
    private Thread thread = new Thread(this);



    //constructor
    public MyFrame() throws IOException {
        //set window size to 800*600
        this.setSize(800,600);
        //set window position in middle
        this.setLocationRelativeTo(null);
        //set window visible
        this.setVisible(true);
        //set window exit
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set size unchangeable
        this.setResizable(false);
        //set keyListener
        this.addKeyListener(this);
        //set window name
        this.setTitle("My Super Cool Super Mario");
        //initialize the images
        StaticValue.init();
        //initialize mario
        mario = new Mario(10,355);


        //create all backgrounds
        for (int i = 1; i <= 3 ; i++) {
            allBg.add(new Background(i,i==3?true:false));
        }
        //set up first background as currentBg
        currentBg = allBg.get(2);
        mario.setBackground(currentBg);
        //paint
        repaint();
        thread.start();
    }

    @Override
    public void paint(Graphics g){
        //If offScreenImage is null, it is created
        // using the createImage method with dimensions 800x600.
        // This off-screen image is used for double-buffering.
        if(offScreenImage == null){
            offScreenImage = createImage(800,600);
        }
        //A Graphics object is obtained from the off-screen image using
        Graphics graphics = offScreenImage.getGraphics();
        //fill background
        graphics.fillRect(0,0,800,600);

        //draw background on buffering area
        //this->visible component
        graphics.drawImage(currentBg.getBgImage(),0,0,this);
        //draw obstacles
        for(Obstacle ob:currentBg.getObstacleList()){
            graphics.drawImage(ob.getShowObstacle(),ob.getX(), ob.getY(),this);
        }
        //draw tower and flagPole
        graphics.drawImage(currentBg.getTower(),620,270,this);
        graphics.drawImage(currentBg.getFlagPole(),500,220,this);
        //draw mario
        graphics.drawImage(mario.getShowMario(),mario.getX(),mario.getY(),this);

        //draw image to window(double buffering process,reduce flickering)
        g.drawImage(offScreenImage,0,0,this);



    }
    public static void main(String[] args) throws IOException {
        //made the window successfully
        MyFrame myFrame = new MyFrame();
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    // let mario move right if player pressed right arrow key
   //let mario move left if player pressed left arrow key
     if(e.getKeyCode() == 39){
         mario.rightMove();
     }
     if(e.getKeyCode() == 37){
         mario.leftMove();
     }
     if(e.getKeyCode() == 38 ){
         mario.jump();
     }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    //let mario stop when player released arrow key
        if(e.getKeyCode() == 39){
            mario.rightStop();
        }
       if(e.getKeyCode() == 37){
            mario.leftStop();
        }
    }

    @Override
    public void run() {
        while(true){
            repaint();
            try {
                Thread.sleep(50);
                if(mario.getX() >= 775){
                    currentBg = allBg.get(currentBg.getCurrentLevel());
                    mario.setBackground(currentBg);
                    mario.setX(10);
                    mario.setY(395);
                }
                //check if game is over
                if(mario.doesReachTower()){
                    JOptionPane.showMessageDialog(this,"Congratulations on successfully completing the Mario game");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
