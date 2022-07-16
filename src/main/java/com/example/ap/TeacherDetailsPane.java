package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Teacher;

import java.io.IOException;

public class TeacherDetailsPane extends Dialog<ButtonType> {
    public Node root;
    public Label teacherNameLabel;
    public Label roomLabel;
    public Label degreeLabel;
    public Label phoneLabel;
    public Label departmentNameLabel;
    public Label emailLabel;

    public static TeacherDetailsPane getView(){
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("teacher_details.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((TeacherDetailsPane) loader.getController()).root = root;
        return loader.getController();
    }

    public void show(Teacher teacher){
        teacherNameLabel.setText(teacherNameLabel.getText() + teacher.name);
        roomLabel.setText(roomLabel.getText() + teacher.roomId);
        degreeLabel.setText(degreeLabel.getText() + teacher.degree);
        phoneLabel.setText(phoneLabel.getText() + teacher.phoneNumber);
        departmentNameLabel.setText(departmentNameLabel.getText() + teacher.department.name);
        emailLabel.setText(emailLabel.getText() + teacher.email);
        getDialogPane().setContent(root);
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        showAndWait();
    }
}
