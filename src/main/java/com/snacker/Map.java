package com.snacker;

/*  KEY FOR MAP CODES
        B   BLANK SPACE WITH NO FOOD
        G   WALLS AND OBSTACLES
        F   REGULAR FOOD
        X   FIREBALL THE MOVES VERTICALLY
        Y   FIREBALL THAT MOVES HORIZONTALLY
        H   MAIN CHARACTER (HERO)
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Map {
    private final int blockSize;
    private final ArrayList<Double> xCordForFireBalls;
    private final ArrayList<Double> yCordForFireBalls;
    private final ArrayList<Double> verticalDistanceToMoveForFireBalls;
    private final ArrayList<Double> horizontalDistanceToMoveForFireBalls;
    private final ArrayList<Double> xCordForHero;
    private final ArrayList<Double> yCordForHero;

    public Map() {
        this.blockSize = 20;
        this.xCordForFireBalls = new ArrayList<>();
        this.yCordForFireBalls = new ArrayList<>();
        this.verticalDistanceToMoveForFireBalls = new ArrayList<>();
        this.horizontalDistanceToMoveForFireBalls = new ArrayList<>();
        this.xCordForHero = new ArrayList<>();
        this.yCordForHero = new ArrayList<>();
    }

    public void generateMap(ArrayList<String> rows, Pane pane, ArrayList<Rectangle> walls, ArrayList<Food> foodList, ArrayList<FireBall> fireBalls, UI ui, ArrayList<Character> segments){
        try{
            File mapFile = new File("maps/map1.txt");
            Scanner openFile = new Scanner(mapFile);
            while (openFile.hasNextLine()) {
                String fileData = openFile.nextLine();
                rows.add(fileData);
            }
            openFile.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //TWO ROWS OF PADDING AT THE TOP OF THE SCREEN FOR SCORE AND INSTRUCTIONS
        int rowNumber = 2;

        for(String row : rows){
            String [] strSplit = row.split("");
            var rowList = new ArrayList<>(
                    Arrays.asList(strSplit)
            );

            int column = 0;

            for (String i: rowList){
                if(!(i.equals("\t"))){
                    Rectangle box = new Rectangle(column*this.blockSize, rowNumber*this.blockSize, this.blockSize, this.blockSize);

                    switch (i) {
                        //WALLS AND BORDERS
                        case "G" -> {
                            box.setFill(Color.BLUE);
                            pane.getChildren().add(box);
                            walls.add(box);
                        }

                        //EMPTY SPACE WITH NO FOOD
                        case "B" -> {
                            box.setFill(Color.BLACK);
                            pane.getChildren().add(box);
                        }

                        //SPACE WITH FOOD
                        case "F" -> addFood(box, foodList, pane);

                        //ENEMY (VERTICAL MOVEMENT) LOCATION WITH FOOD
                        case "X" -> {
                            addFood(box, foodList, pane);
                            this.xCordForFireBalls.add(box.getX());
                            this.yCordForFireBalls.add(box.getY());
                            this.verticalDistanceToMoveForFireBalls.add(5.0);
                            this.horizontalDistanceToMoveForFireBalls.add(0.0);
                        }

                        //ENEMY (HORIZONTAL MOVEMENT) LOCATION WITH FOOD
                        case "Y" -> {
                            addFood(box, foodList, pane);
                            this.xCordForFireBalls.add(box.getX());
                            this.yCordForFireBalls.add(box.getY());
                            this.verticalDistanceToMoveForFireBalls.add(0.0);
                            this.horizontalDistanceToMoveForFireBalls.add(5.0);
                        }

                        //MAIN CHARACTER (HERO) SPACE WITH NO FOOD
                        case "H" -> {
                            box.setFill(Color.BLACK);
                            pane.getChildren().add(box);
                            this.xCordForHero.add(box.getX());
                            this.yCordForHero.add(box.getY());
                        }
                    }
                    column++;
                }
            }
            rowNumber ++;
        }

        //ADD THE FIREBALLS TO THE SCREEN, MUST BE DONE AFTER ADDING FOOD OR THEY WILL NOT BE VISIBLE WHEN MOVING OVER FOOD
        int i = this.xCordForFireBalls.size();
        while (i > 0){
            ui.generateFireBall(fireBalls, pane, xCordForFireBalls.get(i-1), yCordForFireBalls.get(i-1),
                    this.verticalDistanceToMoveForFireBalls.get(i-1), this.horizontalDistanceToMoveForFireBalls.get(i-1));
            i--;
        }

        //ADD HERO TO THE SCREEN, MUST BE DONE AFTER FOOD
        ui.generateHero(segments, pane, this.xCordForHero.get(0), this.yCordForHero.get(0));
    }

    public void addFood(Rectangle box, ArrayList<Food> foodList, Pane pane){
        box.setFill(Color.BLACK);
        Food food = new Food();
        food.getFood().setTranslateX(box.getX()+5);
        food.getFood().setTranslateY(box.getY()+5);
        food.setXPos(box.getX()+5);
        food.setYPos(box.getY()+5);

        foodList.add(food);
        pane.getChildren().add(box);
        pane.getChildren().add(food.getFood());
    }
}
