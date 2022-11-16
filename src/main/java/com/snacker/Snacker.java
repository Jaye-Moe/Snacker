package com.snacker;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;

public class Snacker extends Application {
    @Override
    public void start(Stage stage){
        double moveIncrement = 10;
        double startingXPos = 300;
        double startingYPos = 300;
        final boolean[] gameIsOn = {false};
        final boolean[] turnOver = {false};

        Pane pane = new Pane();
        pane.setPrefSize(420,460);
        Scene scene = new Scene(pane);

        ArrayList<String> rows = new ArrayList<>();
        ArrayList<Character> segments = new ArrayList<>();
        ArrayList<Rectangle> obstacles = new ArrayList<>();
        ArrayList<Food> foodList = new ArrayList<>();
        ArrayList<FireBall> fireBalls = new ArrayList<>();

        UI ui = new UI(moveIncrement, startingXPos, startingYPos);

        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.generateScoreBoard();
        scoreBoard.startingInstructions();
        pane.getChildren().add(scoreBoard.getScoreBoard());
        pane.setStyle("-fx-background-color: black;");

        Map map = new Map();
        map.generateMap(rows, pane, obstacles, foodList, fireBalls, ui, segments);

        java.util.Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        scene.setOnKeyPressed(event -> pressedKeys.put(event.getCode(), Boolean.TRUE));
        scene.setOnKeyReleased(event -> pressedKeys.put(event.getCode(), Boolean.FALSE));

        new AnimationTimer(){
            @Override
            public void handle (long now) {
                if(!gameIsOn[0]){
                    if (pressedKeys.getOrDefault(KeyCode.ENTER, false)) {
                        gameIsOn[0] = true;
                    }
                }

                if(gameIsOn[0] && !turnOver[0]) {
                    if(pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
                        ui.turnLeft(segments, obstacles );
                    }
                    if(pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
                        ui.turnRight(segments, obstacles );
                    }
                    if(pressedKeys.getOrDefault(KeyCode.UP, false)) {
                        ui.turnUp(segments, obstacles );
                    }
                    if(pressedKeys.getOrDefault(KeyCode.DOWN, false)) {
                        ui.turnDown(segments, obstacles );
                    }

                    scoreBoard.displayScore();

                    if(ui.checkHeroForCollisionWithFireBalls(fireBalls, segments)){
                        scoreBoard.looseLife();
                        gameIsOn[0]= false;
                        turnOver[0] = true;
                    }

                    ui.checkForCollisionWithFood(segments, foodList, scoreBoard);

                    if (!ui.checkForCollisionWithWalls(segments, obstacles)) {
                        ui.moveSnakeForward(segments);
                    }

                    ui.checkFireBallsForCollisionWithWalls(fireBalls, obstacles);
                    ui.moveFireBallForward(fireBalls);

                    if (ui.checkForWrapToRight(segments)) {
                        segments.get(0).getCharacter().setTranslateX(420);
                        segments.get(0).updateXPos(420);
                    }
                    if (ui.checkForWrapToLeft(segments)) {
                        segments.get(0).getCharacter().setTranslateX(-10);
                        segments.get(0).updateXPos(-10);

                    }

                }else if (turnOver[0]){
                    scoreBoard.tryAgain();
                    if(scoreBoard.getLives()>0){
                        if (pressedKeys.getOrDefault(KeyCode.ENTER, false)) {
                                segments.get(0).goToStart();
                                gameIsOn[0] = true;
                                turnOver[0] = false;
                        }
                    }else {
                        if (pressedKeys.getOrDefault(KeyCode.ENTER, false)) {
                            gameIsOn[0] = true;
                            turnOver[0] = false;
                            startGame(rows, segments, obstacles, foodList, fireBalls, pane, ui, map, scoreBoard);
                        }
                    }
                }
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
//            }
        }.start();

        stage.setTitle("Snacker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void startGame(ArrayList<String> rows, ArrayList<Character> segments, ArrayList<Rectangle> obstacles, ArrayList<Food> foodList, ArrayList<FireBall> fireBalls,
            Pane pane, UI ui, Map map, ScoreBoard scoreBoard){

        rows.clear();
        segments.clear();
        obstacles.clear();
        foodList.clear();
        fireBalls.clear();
        pane.getChildren().clear();
        scoreBoard.startingInstructions();
        scoreBoard.setLives(3);
        scoreBoard.resetScore();
        pane.getChildren().add(scoreBoard.getScoreBoard());
        map.generateMap(rows, pane, obstacles, foodList, fireBalls, ui, segments);
    }



}