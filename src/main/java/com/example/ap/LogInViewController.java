package com.example.ap;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class LogInViewController {
    public Button logInButton;
    public TextField passwordField;
    public TextField usernameField;
    public Label errorLabel;
    public ImageView captchaImageView;
    public Label captchaErrorLabel;
    public TextField captchaField;
    MyApplication context;
    Captcha captcha;

    public void init(MyApplication context) {
        this.context = context;
        errorLabel.setText("");
        captchaErrorLabel.setText("");
        refresh();
    }

    public void OnLogInClicked() throws IOException {
        if (!captchaField.getText().equals(captcha.answer)) {
            init(context);
            captchaErrorLabel.setText("Captcha is Wrong!!!");
        } else {
            for (User user : User.allUsers) {
                if (user.username.equals(usernameField.getText().trim()) && user.password.equals(Hash.hash(passwordField.getText()))) {
                    if (user.lastLogIn.plusHours(3).isBefore(LocalDateTime.now())) {
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setHeaderText("Please enter a new password");
                        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                        Pane pane = new Pane();
                        TextField field = new TextField();
                        field.setLayoutY(25);
                        field.setLayoutX(25);
                        field.setPromptText("New Password");
                        pane.getChildren().add(field);
                        dialog.getDialogPane().setContent(pane);
                        do {
                            field.setText("");
                            dialog.showAndWait();
                            dialog.setHeaderText("New password is same as old password.");
                        } while (field.getText().equals(user.password) || dialog.getResult() != ButtonType.OK);
                        user.setPassword(field.getText());
                    }
                    context.mainPage(user);
                    return;
                }
            }

            init(context);
            errorLabel.setText("Username or Password is Wrong!");
        }
    }

    public void refresh() {
        captcha = CaptchaBuilder.getRandomCaptcha();
        captchaImageView.setImage(captcha.image);
    }
}
