package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import logic.FinalMark;
import logic.User;

import java.io.IOException;

public class StudentMarkCell extends ListCell<FinalMark> {
    public Node root;
    public Label nameLabel;
    public Label numberLabel;
    public TextField markField;
    public Button objectionButton;
    public User user;

    public StudentMarkCell(User user) {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("student_mark_cell.fxml"));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(null);
        setPadding(new Insets(0));
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

    @Override
    protected void updateItem(FinalMark finalMark, boolean b) {
        super.updateItem(finalMark, b);
        if (finalMark == null || b) {
            setGraphic(null);
        } else {
            setGraphic(root);
            nameLabel.setText(finalMark.student.name);
            numberLabel.setText(finalMark.student.studentNumber);
            markField.setText(finalMark.value + "");
        }
    }
}
