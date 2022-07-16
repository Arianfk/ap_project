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

public class CourseResultCell extends ListCell<FinalMark> {
    public Node root;
    public Label courseNameLabel;
    public Button objectButton;
    public TextField markField;
    public User user;

    public CourseResultCell(User user) {
        this.user = user;
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("course_result_cell.fxml"));
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
}
