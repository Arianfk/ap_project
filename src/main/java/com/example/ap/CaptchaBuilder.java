package com.example.ap;

import javafx.scene.image.Image;

public class CaptchaBuilder {
    public static int n = 6;
    public static String[] answers = {"163356", "651606", "991181", "742336", "665648", "321404"};

    public static Captcha getRandomCaptcha() {
        int x = (int) Math.floor(Math.random() * n);

        Image image = new Image("captcha(" + x + ").png", 160, 90, false, true);
        return new Captcha(answers[x], image);
    }
}
