package com.snacker;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FireBall extends Character {
    public FireBall(){
        this.imageFile = new Image("file:images/fireball.gif");
        this.image = new ImageView(this.imageFile);
    }
}