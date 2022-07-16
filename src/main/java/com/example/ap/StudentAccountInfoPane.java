package com.example.ap;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Student;

public class StudentAccountInfoPane extends FragmentPane {
    public Label nameLabel;
    public Label nationalCodeLabel;
    public Label studentCodeLabel;
    public Label markLabel;
    public Label departmentLabel;
    public Label teacherLabel;
    public Label enterYearLabel;
    public Label degreeLabel;
    public Label stateLabel;
    public ImageView userImageView;
    public TextField emailField;
    public TextField phoneNumberField;

    public void load(Student student) {
        this.student = student;

        nameLabel.setText("Name: " + student.name);
        nationalCodeLabel.setText("National Code: " + student.nationalCode);
        studentCodeLabel.setText("Student ID: " + student.studentNumber);
        markLabel.setText("Total Average: " + student.getAverage());
        departmentLabel.setText("Department of " + student.department.name);
        teacherLabel.setText("Guide Name: " + student.guide.name);
        enterYearLabel.setText("Entered " + student.enterYear);
        degreeLabel.setText("Degree " + student.degree);
        stateLabel.setText("State: " + student.eduStatus.name());
        emailField.setText(student.email);
        phoneNumberField.setText(student.phoneNumber);
        userImageView.setImage(new Image("profile.png", 200, 200, false, true));
    }

    public void save() {
        student.setEmail(emailField.getText());
        student.setPhoneNumber(phoneNumberField.getText());
    }
}
