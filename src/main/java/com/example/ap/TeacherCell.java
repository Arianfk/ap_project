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
import logic.Teacher;
import logic.User;

import java.io.IOException;

public class TeacherCell extends ListCell<Teacher> {
    public Label teacherNameLabel;
    public Label departmentLabel;
    public Button detButton;
    Node root;
    public User user;

    public TeacherCell(User user) {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("teacher_cell.fxml"));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(null);
    }

    @Override
    protected void updateItem(Teacher teacher, boolean b) {
        super.updateItem(teacher, b);
        if (teacher != null) {
            teacherNameLabel.setText(teacher.name);
            departmentLabel.setText(teacher.department.name);
            detButton.setText("info");
            setPadding(new Insets(0));
            setGraphic(root);
        } else {
            setGraphic(null);
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
