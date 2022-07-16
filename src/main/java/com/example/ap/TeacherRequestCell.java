package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import logic.Request;
import logic.User;

import java.io.IOException;

public class TeacherRequestCell extends ListCell<Request> {
    public Node root;
    public Label nameLabel;
    public Label numberLabel;
    public Button acceptButton;
    public Button declineButton;
    public User user;

    public TeacherRequestCell(User user) {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("teacher_request_cell.fxml"));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setPadding(new Insets(0));
    }

    @Override
    protected void updateItem(Request request, boolean b) {
        super.updateItem(request, b);
        if (request == null || b) {
            setGraphic(null);
            setBackground(null);
        } else {
            setGraphic(root);
            nameLabel.setText(request.from.name);
            numberLabel.setText(request.from.studentNumber);
        }
    }

    @Override
    public void updateSelected(boolean b) {
        super.updateSelected(b);
        if (b) {
            setBackground(new Background(new BackgroundFill(user.theme.listViewSelectionColor, new CornerRadii(10), new Insets(5))));
        } else {
            setBackground(null);
        }
    }
}
