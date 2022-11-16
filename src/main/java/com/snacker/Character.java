package com.snacker;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character {
    public Image imageFile;
    public ImageView image;
    private double xPos;
    private double yPos;
    private final double width;
    private final double height;
    private double verticalDistanceToMove;
    private double horizontalDistanceToMove;
    private double startingX;
    private double startingY;
    private int startingAngle;


    public Character(){
        this.imageFile = new Image("file:images/right.gif");
        this.image = new ImageView(this.imageFile);
        this.xPos = 300;
        this.yPos = 300;
        this.startingX = 0;
        this.startingY = 0;
        this.startingAngle=0;
        this.verticalDistanceToMove = -10; // Start the game going up by default.
        this.horizontalDistanceToMove = 0;
        this.height = imageFile.getHeight();
        this.width = imageFile.getWidth();

    }
    public ImageView getCharacter(){
        return this.image;
    }

    public double getXPos(){
        return this.xPos;
    }

    public double getYPos(){
        return this.yPos;
    }

    public void updateXPos(double xPos){
        this.xPos = xPos;
    }

    public void updateYPos(double yPos){
        this.yPos = yPos;
    }

    public double getVerticalDistanceToMove() {
        return verticalDistanceToMove;
    }

    public double getHorizontalDistanceToMove() {
        return horizontalDistanceToMove;
    }

    public void setVerticalDistanceToMove(double verticalDistanceToMove) {
        this.verticalDistanceToMove = verticalDistanceToMove;
    }

    public void setHorizontalDistanceToMove(double horizontalDistanceToMove) {
        this.horizontalDistanceToMove = horizontalDistanceToMove;
    }

    public Rectangle2D getBoundary(){
        return new Rectangle2D(this.xPos+this.horizontalDistanceToMove, this.yPos+this.verticalDistanceToMove, this.width, this.height);
    }

    public void turn(double angle){
        this.image.setRotate(0);
        this.image.setRotate(angle);
    }

    public void setStartingCoordinates(Double startingX, Double startingY, int startingAngle){
        this.startingX=startingX;
        this.startingY=startingY;
        this.startingAngle=startingAngle;
    }

    public void goToStart(){
        this.turn(this.startingAngle);
        this.updateXPos(this.startingX);
        this.updateYPos(this.startingY);
        this.image.setTranslateX(startingX);
        this.image.setTranslateY(startingY);
        this.verticalDistanceToMove=0;
        this.horizontalDistanceToMove=0;
    }

}
