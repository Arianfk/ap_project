package com.example.ap;

import javafx.scene.image.Image;

public class Captcha {
    public String answer;
    public Image image;

    public Captcha(String answer, Image image) {
        this.answer = answer;
        this.image = image;
    }
}
