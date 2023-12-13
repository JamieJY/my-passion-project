package com.sxt;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Background {
    //show current background
    private BufferedImage bgImage = null;
    // current game level
    private int currentLevel;
    // check if this is the last level
    private boolean isLastLevel;

    private List<Obstacle> obstacleList = new ArrayList<>();
    private BufferedImage flagPole = null;
    private BufferedImage tower = null;
    private boolean doesReachFlagPole = false;
    private boolean isFlagLowering = false;

    public Background() {

    }

    public Background(int currentLevel, boolean isLastLevel) {
        this.currentLevel = currentLevel;
        this.isLastLevel = isLastLevel;

        if (isLastLevel) {
            bgImage = StaticValue.bg2;
        } else {
            bgImage = StaticValue.bg;
        }

        //currentLevel = first level
        /*
        0 30 60 90 120 ......800
        30 30 60 90 120 ......800
        60 30 60 90 120 ......800
        90 30 60 90 120 ......800
        .
        .
        600 30 60 90 120 ......800
        */
        if (currentLevel == 1) {
            //up_ground type=1
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            //below_ground type=2
            for (int j = 0; j <= 120; j += 30) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 30, 570 - j, 2, this));
                }
            }
            //draw blue bricks(type = 7) and orange bricks(type = 0) with position
            for (int i = 120; i <= 150; i += 30) {
                obstacleList.add(new Obstacle(i, 300, 7, this));
            }
            for (int i = 300; i <= 570; i += 30) {
                if (i == 360 || i == 480 || i == 510 || i == 540) {
                    obstacleList.add(new Obstacle(i, 300, 7, this));
                } else {
                    obstacleList.add(new Obstacle(i, 300, 0, this));
                }
            }
            for (int i = 420; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240, 7, this));
            }
            // draw light blue pipes(type 3 and 5) and blue pipes(type 4 and 6)
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 3, this));
                    obstacleList.add(new Obstacle(645, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                }
            }
        }
        //game level 2
        if (currentLevel == 2) {
            //up_ground type=1
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            //below_ground type=2
            for (int j = 0; j <= 120; j += 30) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 30, 570 - j, 2, this));
                }
            }
            //pipes
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(60, i, 3, this));
                    obstacleList.add(new Obstacle(85, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(60, i, 5, this));
                    obstacleList.add(new Obstacle(85, i, 6, this));
                }
            }
            for (int i = 330; i <= 600; i += 25) {
                if (i == 360) {
                    obstacleList.add(new Obstacle(620, i, 3, this));
                    obstacleList.add(new Obstacle(645, i, 4, this));
                } else {
                    obstacleList.add(new Obstacle(620, i, 5, this));
                    obstacleList.add(new Obstacle(645, i, 6, this));
                }
            }
            //bricks
            obstacleList.add(new Obstacle(300, 330, 0, this));

            for (int i = 270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i, 360, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 360, 7, this));
                }
            }
            for (int i = 240; i <= 360; i += 30) {
                if (i == 240 || i == 360) {
                    obstacleList.add(new Obstacle(i, 390, 0, this));
                } else {
                    obstacleList.add(new Obstacle(i, 390, 7, this));
                }
            }
            obstacleList.add(new Obstacle(240, 300, 0, this));
            for (int i = 360; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i, 270, 7, this));
            }
        }
        //game level 3
        if (currentLevel == 3) {
            //up_ground type=1
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 30, 420, 1, this));
            }
            //below_ground type=2
            for (int j = 0; j <= 120; j += 30) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 30, 570 - j, 2, this));
                }
            }

            //bricks
            int temp = 290;
            for (int i = 390; i >= 270; i -= 30) {
                for (int j = temp; j <= 410; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 7, this));
                }
                temp += 30;
            }
            temp = 60;
            for (int i = 390; i >= 360; i -= 30) {
                for (int j = temp; j <= 90; j += 30) {
                    obstacleList.add(new Obstacle(j, i, 7, this));
                }
                temp += 30;
            }
            //flag pole
            flagPole = StaticValue.flagPole;
            tower = StaticValue.tower;
            obstacleList.add(new Obstacle(515, 220, 8, this));
        }
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public boolean isLastLevel() {
        return isLastLevel;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public boolean doesReachFlagPole() {
        return doesReachFlagPole;
    }

    public void setDoesReachFlagPole(boolean reach) {
        doesReachFlagPole = reach;
    }

    public boolean isFlagLowering() {
        return isFlagLowering;
    }

    public void setFlagLowering(boolean flagLowering) {
        isFlagLowering = flagLowering;
    }

    public BufferedImage getFlagPole() {
        return flagPole;
    }

    public BufferedImage getTower() {
        return tower;
    }
}
