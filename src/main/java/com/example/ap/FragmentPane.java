package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import logic.Student;
import logic.Teacher;

import java.io.IOException;

public class FragmentPane {
    public Node root;
    public Student student;
    public Teacher teacher;
    String name = "";

    public static FragmentPane getView(String fxmlName) {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource(fxmlName));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FragmentPane controller = loader.getController();
        controller.root = root;
        ((Pane) root).setBackground(new Background(new BackgroundFill(MainPageViewController.theme.backgroundColor, CornerRadii.EMPTY, new Insets(0))));
        return controller;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void load(Student student) {
        this.student = student;
    }

    public void load(Teacher teacher) {
        this.teacher = teacher;
    }
}
