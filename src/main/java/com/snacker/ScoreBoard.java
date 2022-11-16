package com.snacker;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.text.NumberFormat;

public class ScoreBoard {
    private final Label scoreBoard;

    private int score;
    private int level;
    private int lives;
    private final NumberFormat nf;

    public ScoreBoard(){
        this.scoreBoard = new Label();
        this.score = 0;
        this.level = 1;
        this.lives =3;
        this.nf = NumberFormat.getInstance();
    }

    public void generateScoreBoard(){
        this.scoreBoard.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 16));
        this.scoreBoard.setTextFill(Color.CYAN);
        this.displayScore();
    }

    public void displayScore(){
        this.scoreBoard.setText("Score: " + this.nf.format(this.score) +"                   Level: " + this.level +"              Lives: " + this.lives);
    }

    public Label getScoreBoard(){
        return this.scoreBoard;
    }

    public void increaseScore(){
        this.score++;
        this.displayScore();
    }

    public void resetScore(){
        this.score = 0;
        this.displayScore();
    }

    public void gameOver(){
        this.scoreBoard.setText("Score: " + this.nf.format(this.score) +"               Level: " + this.level +
                " \n         GAME OVER - Press Enter to Reset");
    }

    public void startingInstructions(){
        this.scoreBoard.setText("Score: " + this.nf.format(this.score) +"                   Level: " + this.level +"              Lives: " + this.lives +"" +
                "\n                      Press Enter to Start");
    }

    public void tryAgain() {
        if (this.lives == 0) {
            this.gameOver();
        } else {
            this.scoreBoard.setText("Score: " + this.nf.format(this.score) + "                   Level: " + this.level + "              Lives: " + this.lives + "" +
                    "\n   You were burnt.  Press Enter to try again.");
        }
    }

    public void looseLife(){
        this.lives--;
    }

    public int getLives(){
        return this.lives;
    }

    public void setLives(int lives){
        this.lives =lives;
    }



}

