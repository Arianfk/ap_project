package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.Pair;
import logic.Objection;

import java.io.IOException;

public class StudentObjectionDialog extends Dialog<Pair<String, ButtonType>> {
    public Node root;
    public Objection objection;
    public TextField objectTextField;
    public Label answerLabel;

    public StudentObjectionDialog(Objection objection) {
        super();
        this.objection = objection;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("student_objection_dialog.fxml"));
        loader.setController(this);
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.root = root;
        getDialogPane().setContent(this.root);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        if (objection == null) {
            getDialogPane().getButtonTypes().add(ButtonType.APPLY);
            objectTextField.setEditable(true);
        } else {
            objectTextField.setText(objection.object);
            objectTextField.setEditable(false);
            if (objection.isAnswered) {
                answerLabel.setText(objection.ans);
            }
        }

        setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY) {
                return new Pair<>(objectTextField.getText(), buttonType);
            }
            return new Pair<>("", buttonType);
        });
    }
}
