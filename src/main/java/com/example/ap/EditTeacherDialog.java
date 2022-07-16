package com.example.ap;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import logic.Teacher;
import logic.TeacherDegree;

import java.io.IOException;

public class EditTeacherDialog extends Dialog<ButtonType> {
    public TextField nameField;
    public TextField usernameField;
    public TextField roomField;
    public ComboBox<TeacherDegree> degreeCombo;
    public Teacher teacher;
    public TextField passwordField;

    public EditTeacherDialog() {
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL, new ButtonType("Delete", ButtonBar.ButtonData.LEFT));

        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("edit_teacher_dialog.fxml"));
        loader.setController(this);
        try {
            getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        degreeCombo.setItems(FXCollections.observableArrayList(TeacherDegree.values()));
    }

    public void load(Teacher teacher) {
        this.teacher = teacher;

        nameField.setText(teacher.name);
        usernameField.setText(teacher.username);
        roomField.setText(teacher.roomId);
        degreeCombo.setValue(teacher.degree);
    }
}
