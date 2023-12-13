package com.sxt;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    //mario position(x,y)
    private int x;
    private int y;
    //mario current status
    private String currentStatus;
    private BufferedImage showMario = null;

    //get current obstacle information in the background
    private Background background = new Background();

    private Thread thread = null;

    //mario speed on x/moveSpeed and y/jumpSpeed
    private int xSpeed;
    private int ySpeed;
    //index used to get mario's current move image
    private int index;
    //the time that mario goes up
    private int upTime = 0;

    private boolean doesReachTower;

    public Mario() {

    }

    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        //stating image of mario
        showMario = StaticValue.standRight;
        this.currentStatus = "stand--right";
        thread = new Thread(this);
        thread.start();

    }

    public void leftMove() {
        xSpeed = -5;
        //if mario reaches flag pole
        if (background.doesReachFlagPole()) {
            xSpeed = 0;
        }

        if (currentStatus.indexOf("jump") != -1) {
            //mario in the air cannot move,
            currentStatus = "jump--left";
        } else {
            //mario moves to left
            currentStatus = "move--left";
        }
    }

    public void rightMove() {
        xSpeed = 5;
        //if mario reaches flag pole
        if (background.doesReachFlagPole()) {
            xSpeed = 0;
        }
        if (currentStatus.indexOf("jump") != -1) {
            //mario in the air cannot move,
            currentStatus = "jump--right";
        } else {
            //mario moves to right
            currentStatus = "move--right";
        }
    }

    public void leftStop() {
        xSpeed = 0;
        if (currentStatus.indexOf("jump") != -1) {
            //mario in the air cannot move,you cannot stop mario in the air
            currentStatus = "jump--left";
        } else {
            //mario stops to left
            currentStatus = "stop--left";
        }
    }

    public void rightStop() {
        xSpeed = 0;
        if (currentStatus.indexOf("jump") != -1) {
            //mario in the air cannot move,you cannot stop mario in the air
            currentStatus = "jump--right";
        } else {
            //mario stops to right
            currentStatus = "stop--right";
        }
    }

    public void jump() {
        if (currentStatus.indexOf("jump") == -1) {
            //currentStatus is not jumping
            if (currentStatus.indexOf("left") != -1) {
                //left position
                currentStatus = "jump--left";
            } else {
                currentStatus = "jump--right";
            }
            ySpeed = -10;
            upTime = 7;
            //jump up 70
        }
        //if mario reaches flag pole
        if (background.doesReachFlagPole()) {
            ySpeed = 0;
        }
    }

    public void fall() {
        if (currentStatus.indexOf("left") != -1) {
            currentStatus = "jump--left";
        } else {
            currentStatus = "jump--right";
        }
        ySpeed = 10;

    }

    @Override
    public void run() {
        while (true) {
            //if mario is on an obstacle
            boolean isOnObstacle = false;
            boolean canMoveRight = true;
            boolean canMoveLeft = true;

            if (background.isLastLevel()&& this.x >= 500) {
                this.background.setDoesReachFlagPole(true);
                //if flag lowering is finished
                //if (this.background.isFlagLowering()) {
                if (this.background.doesReachFlagPole() && y == 395) {
                    //mario moves to tower
                    currentStatus = "move--right";
                    if (x < 690) {
                        x = x + 5;
                    } else {
                        doesReachTower = true;
                    }
                } else {
                    //mario in the air
                    if (y < 395) {
                        xSpeed = 0;
                        this.y += 5;
                        currentStatus = "jump-right";
                    }
                    if (y > 395) {
                        this.y = 395;
                        currentStatus = "stop--right";
                    }
                }
            } else {

                //loop all obstacles
                for (int i = 0; i < background.getObstacleList().size(); i++) {
                    Obstacle ob = background.getObstacleList().get(i);
                    //check if mario is on an obstacle,y is fixed and x has a range
                    if (ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        isOnObstacle = true;
                    }
                    // touch obstacle if mario jumps up
                    if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) &&
                            (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)) {
                        if (ob.getType() == 0) {
                            background.getObstacleList().remove(ob);
                        }
                        upTime = 0;
                    }
                    //mario meets obstacle on right/left
                    if (ob.getX() == this.x + 25 && ob.getY() > this.y - 30 && ob.getY() < this.y + 25) {
                        canMoveRight = false;
                    }
                    if (ob.getX() == this.x - 30 && ob.getY() > this.y - 30 && ob.getY() < this.y + 25) {
                        canMoveLeft = false;
                    }
                }


                //mario movement on obstacle,going up or falling down or move on obstacle
                if (isOnObstacle && upTime == 0) {
                    if (currentStatus.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            currentStatus = "move-left";
                        } else {
                            currentStatus = "stop--left";
                        }
                    } else {
                        if (xSpeed != 0) {
                            currentStatus = "move--right";
                        } else {
                            currentStatus = "stop--right";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y = y + ySpeed;
                }
            }

            if ((canMoveLeft && xSpeed < 0) || (canMoveRight && xSpeed > 0)) {
                //mario is moving,change his position
                x = x + xSpeed;
                //check if mario is on the left boarder of frame
                if (x < 0) {
                    x = 0;
                }
            }
            //if mario is moving,change index to change two different moving images
            if (currentStatus.contains("move")) {
                index = index == 0 ? 1 : 0;
            }
            //if mario is left moving
            if (currentStatus.equalsIgnoreCase("move--left")) {
                showMario = StaticValue.runLeft.get(index);
            }
            //if mario is right moving
            if (currentStatus.equalsIgnoreCase("move--right")) {
                showMario = StaticValue.runRight.get(index);
            }
            //if mario stops left/right
            if (currentStatus.equalsIgnoreCase("stop--left")) {
                showMario = StaticValue.standLeft;
            }
            if (currentStatus.equalsIgnoreCase("stop--right")) {
                showMario = StaticValue.standRight;
            }
            if (currentStatus.equalsIgnoreCase("jump--left")) {
                showMario = StaticValue.jumpLeft;
            }
            if (currentStatus.equalsIgnoreCase("jump--right")) {
                showMario = StaticValue.jumpRight;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShowMario() {
        return showMario;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public void setShowMario(BufferedImage showMario) {
        this.showMario = showMario;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean doesReachTower() {
        return doesReachTower;
    }

}
