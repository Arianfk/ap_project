package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import logic.MinorRequest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class StudentMinorRequestDialog extends Dialog<ButtonType> {
    public Node root;
    public Label dateLabel;
    public Label homeNameLabel;
    public Label desNameLabel;
    public Label homeStatusLabel;
    public Label desStatusLabel;


    public StudentMinorRequestDialog() {
        super();
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    public static StudentMinorRequestDialog createNew() {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("student_minor_request_dialog.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StudentMinorRequestDialog pane = loader.getController();
        pane.root = root;
        pane.getDialogPane().setContent(root);
        return pane;
    }

    public void load(MinorRequest request) {
        dateLabel.setText(dateLabel.getText() + request.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        homeNameLabel.setText(homeNameLabel.getText() + request.home.name);
        desNameLabel.setText(desNameLabel.getText() + request.des.name);
        homeStatusLabel.setText(homeStatusLabel.getText() + request.getHomeStatus().name());
        desStatusLabel.setText(desStatusLabel.getText() + request.getDesStatus().name());
    }
}
