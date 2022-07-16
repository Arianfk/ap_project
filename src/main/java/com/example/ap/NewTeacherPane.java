package com.example.ap;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import logic.Teacher;
import logic.TeacherDegree;
import logic.User;

import java.time.LocalDate;

public class NewTeacherPane extends FragmentPane {
    public TextField usernameField;
    public TextField passwordField;
    public TextField nameField;
    public DatePicker birthDatePicker;
    public TextField codeField;
    public ComboBox<TeacherDegree> degreeCombo;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        degreeCombo.setItems(FXCollections.observableArrayList(TeacherDegree.values()));
    }

    public void save() {
        if (User.findByUsername(usernameField.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username is already used!");
            alert.showAndWait();
            return ;
        }
        Teacher teacher = new Teacher(usernameField.getText(), passwordField.getText());
        teacher.birthDay = birthDatePicker.getValue();
        teacher.enterYear = LocalDate.now().getYear() + "";
        teacher.name = nameField.getText();
        teacher.nationalCode = codeField.getText();
        teacher.degree = degreeCombo.getValue();
        teacher.setDepartment(this.teacher.department);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Successfully Saved");
        alert.showAndWait();
    }
}
