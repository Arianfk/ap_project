package com.example.ap;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import logic.Degree;
import logic.Student;
import logic.Teacher;
import logic.User;

import java.time.LocalDate;

public class NewStudentPane extends FragmentPane {

    public TextField usernameField;
    public TextField passwordField;
    public DatePicker birthDatePicker;
    public TextField nameField;
    public TextField codeField;
    public ComboBox<Degree> degreeCombo;
    public ComboBox<Teacher> guideCombo;

    @Override
    public void load(Teacher teacher) {
        this.teacher = teacher;

        degreeCombo.setItems(FXCollections.observableArrayList(Degree.values()));
        guideCombo.setItems(FXCollections.observableArrayList(teacher.department.teacherList));
    }

    public void save() {
        if (User.findByUsername(usernameField.getText()) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username is already used!");
            alert.showAndWait();
            return ;
        }
        Student student = new Student(usernameField.getText(), passwordField.getText());
        student.birthDay = birthDatePicker.getValue();
        student.enterYear = LocalDate.now().getYear() + "";
        student.name = nameField.getText();
        student.nationalCode = codeField.getText();
        student.degree = degreeCombo.getValue();
        student.guide = guideCombo.getValue();
        student.setDepartment(teacher.department);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Successfully Saved");
        alert.showAndWait();
    }
}
