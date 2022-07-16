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
import logic.Course;
import logic.User;

import java.io.IOException;

public class CourseCell extends ListCell<Course> {
    public Label nameLabel;
    public Label teacherLabel;
    public Button detButton;
    public User user;
    Node root;

    public CourseCell(User user) {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("course_item.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBackground(null);
    }

    @Override
    protected void updateItem(Course course, boolean b) {
        super.updateItem(course, b);
        if (course != null) {
            nameLabel.setText(course.name);
            teacherLabel.setText(course.teacher.name);
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
