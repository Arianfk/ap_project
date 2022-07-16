package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import logic.RecomRequest;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class StudentRecomRequestDetDialog extends Dialog<ButtonType> {
    public Node root;
    public Label teacherName;
    public Label dateLabel;
    public Label statusLabel;
    public Label answerLabel;

    public StudentRecomRequestDetDialog() {
        super();
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    public static StudentRecomRequestDetDialog createNew() {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("student_recom_request_det_dialog.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ((StudentRecomRequestDetDialog) loader.getController()).getDialogPane().setContent(root);
        return loader.getController();
    }

    public void load(RecomRequest request) {
        teacherName.setText(teacherName.getText() + request.to.name);
        dateLabel.setText(dateLabel.getText() + request.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        statusLabel.setText(statusLabel.getText() + request.status.name());
        if (request.answer != null) {
            answerLabel.setText(request.answer);
        }
    }
}
