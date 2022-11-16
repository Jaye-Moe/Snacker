package com.snacker;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class UI {
    double moveIncrement;
    double startingXPos;
    double startingYPos;
    double oldHorizonalMovement;
    double oldVerticalMovement;

    public UI(double moveIncrement, double startingXPos, double startingYPos){
        this.moveIncrement = moveIncrement;
        this.startingXPos = startingXPos;
        this.startingYPos = startingYPos;
        this.oldHorizonalMovement = 0;
        this.oldVerticalMovement=0;}

    public boolean checkForCollisionWithWalls(ArrayList<Character> segments, ArrayList<Rectangle> obstacles ){
        for (Rectangle i: obstacles){
            if(segments.get(0).getBoundary().intersects(i.getX(), i.getY(),20, 20)){
                    return true;
                }
        }
        return false;
    }
    
    public void checkFireBallsForCollisionWithWalls(ArrayList<FireBall> fireBalls, ArrayList<Rectangle> obstacles ){
        for (Rectangle i: obstacles) {
            for (FireBall j : fireBalls) {
                if (j.getBoundary().intersects(i.getX(), i.getY(), 20, 20)) {
                    j.setVerticalDistanceToMove(j.getVerticalDistanceToMove() * -1);
                    j.setHorizontalDistanceToMove(j.getHorizontalDistanceToMove()*-1);
                }
            }
        }
    }

    public boolean checkHeroForCollisionWithFireBalls(ArrayList<FireBall> fireBalls, ArrayList<Character> segments) {
        for (Character i : segments) {
            for (FireBall j : fireBalls) {
                if (i.getBoundary().intersects(j.getBoundary())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkForWrapToRight (ArrayList<Character> segments){
        return segments.get(0).getXPos() < 0 && segments.get(0).getYPos() == 220;
    }

    public boolean checkForWrapToLeft (ArrayList<Character> segments){
        return segments.get(0).getXPos() > 420 && segments.get(0).getYPos() == 220;
    }

    public void checkForCollisionWithFood(ArrayList<Character> segments, ArrayList<Food> foodList, ScoreBoard scoreBoard){
        for (Food i: foodList){
            if(segments.get(0).getBoundary().intersects(i.getXPos(), i.getYPos(),0,0)){
                i.getFood().setTranslateX(1000);
                i.setXPos(1000);
                scoreBoard.increaseScore();
            }
        }
    }

    public void moveSnakeForward( ArrayList<Character> segments){
        int numberOfSegments = segments.size() - 1;

        while (numberOfSegments > 0) {
            segments.get(numberOfSegments).getCharacter().setTranslateY(segments.get(numberOfSegments - 1).getYPos());
            segments.get(numberOfSegments).getCharacter().setTranslateX(segments.get(numberOfSegments - 1).getXPos());
            segments.get(numberOfSegments).updateYPos(segments.get(numberOfSegments).getCharacter().getTranslateY());
            segments.get(numberOfSegments).updateXPos(segments.get(numberOfSegments).getCharacter().getTranslateX());
            numberOfSegments--;
        }

        segments.get(0).getCharacter().setTranslateY(segments.get(0).getYPos() + segments.get(0).getVerticalDistanceToMove());
        segments.get(0).updateYPos(segments.get(0).getCharacter().getTranslateY());
        segments.get(0).getCharacter().setTranslateX(segments.get(0).getXPos() + segments.get(0).getHorizontalDistanceToMove());
        segments.get(0).updateXPos(segments.get(0).getCharacter().getTranslateX());
    }

    public void turnUp(ArrayList<Character> segments, ArrayList<Rectangle> obstacles){
        this.oldHorizonalMovement = segments.get(0).getHorizontalDistanceToMove();
        this.oldVerticalMovement = segments.get(0).getVerticalDistanceToMove();
        segments.get(0).setHorizontalDistanceToMove(0);
        segments.get(0).setVerticalDistanceToMove(-moveIncrement);

        if(this.checkForCollisionWithWalls(segments,obstacles)){
            segments.get(0).setHorizontalDistanceToMove(this.oldHorizonalMovement);
            segments.get(0).setVerticalDistanceToMove(this.oldVerticalMovement);

        }else {
            segments.get(0).setHorizontalDistanceToMove(0);
            segments.get(0).setVerticalDistanceToMove(-moveIncrement);
            segments.get(0).turn(270);
        }
    }

    public void turnDown(ArrayList<Character> segments, ArrayList<Rectangle> obstacles) {
        this.oldHorizonalMovement = segments.get(0).getHorizontalDistanceToMove();
        this.oldVerticalMovement = segments.get(0).getVerticalDistanceToMove();
        segments.get(0).setHorizontalDistanceToMove(0);
        segments.get(0).setVerticalDistanceToMove(moveIncrement);

        if (this.checkForCollisionWithWalls(segments, obstacles)) {
            segments.get(0).setHorizontalDistanceToMove(this.oldHorizonalMovement);
            segments.get(0).setVerticalDistanceToMove(this.oldVerticalMovement);

        } else {
            segments.get(0).setHorizontalDistanceToMove(0);
            segments.get(0).setVerticalDistanceToMove(moveIncrement);
            segments.get(0).turn(90);
        }
    }

    public void turnLeft(ArrayList<Character> segments, ArrayList<Rectangle> obstacles) {
        this.oldHorizonalMovement = segments.get(0).getHorizontalDistanceToMove();
        this.oldVerticalMovement = segments.get(0).getVerticalDistanceToMove();
        segments.get(0).setHorizontalDistanceToMove(-moveIncrement);
        segments.get(0).setVerticalDistanceToMove(0);

        if(this.checkForCollisionWithWalls(segments,obstacles)) {
            segments.get(0).setHorizontalDistanceToMove(this.oldHorizonalMovement);
            segments.get(0).setVerticalDistanceToMove(this.oldVerticalMovement);
        }else {
            segments.get(0).setHorizontalDistanceToMove(-moveIncrement);
            segments.get(0).setVerticalDistanceToMove(0);
            segments.get(0).turn(180);
        }
    }

    public void turnRight(ArrayList<Character> segments, ArrayList<Rectangle> obstacles) {
        this.oldHorizonalMovement = segments.get(0).getHorizontalDistanceToMove();
        this.oldVerticalMovement = segments.get(0).getVerticalDistanceToMove();
        segments.get(0).setHorizontalDistanceToMove(moveIncrement);
        segments.get(0).setVerticalDistanceToMove(0);

        if(this.checkForCollisionWithWalls(segments,obstacles)) {
            segments.get(0).setHorizontalDistanceToMove(this.oldHorizonalMovement);
            segments.get(0).setVerticalDistanceToMove(this.oldVerticalMovement);
        }else{
            segments.get(0).setHorizontalDistanceToMove(moveIncrement);
            segments.get(0).setVerticalDistanceToMove(0);
            segments.get(0).turn(0);
        }
    }

    public void moveFireBallForward(ArrayList<FireBall> fireBalls) {

        for (FireBall i: fireBalls) {

            i.getCharacter().setTranslateY(i.getYPos() + i.getVerticalDistanceToMove());
            i.updateYPos(i.getCharacter().getTranslateY());
            i.getCharacter().setTranslateX(i.getXPos() + i.getHorizontalDistanceToMove());
            i.updateXPos(i.getCharacter().getTranslateX());
        }
    }

    public void generateHero(ArrayList<Character> segments, Pane pane, Double xCord, Double yCord){
        Character hero = new Character();
        segments.add(hero);
        segments.get(0).getCharacter().setTranslateX(xCord);
        segments.get(0).getCharacter().setTranslateY(yCord);
        segments.get(0).updateXPos(xCord);
        segments.get(0).updateYPos(yCord);
        segments.get(0).turn(270);
        segments.get(0).setStartingCoordinates(xCord,yCord,270);
        pane.getChildren().add(hero.getCharacter());
    }

    public void generateFireBall(ArrayList<FireBall> fireBalls, Pane pane, Double xCord, Double yCord, Double verticalDistanceToMove, Double horizontalDistanceToMove){
        FireBall fireBall = new FireBall();
        fireBall.getCharacter().setTranslateX(xCord);
        fireBall.updateXPos(xCord);
        fireBall.getCharacter().setTranslateY(yCord);
        fireBall.updateYPos(yCord);
        fireBalls.add(fireBall);
        fireBall.setVerticalDistanceToMove(verticalDistanceToMove);
        fireBall.setHorizontalDistanceToMove(horizontalDistanceToMove);
        pane.getChildren().add(fireBall.getCharacter());
    }
}


