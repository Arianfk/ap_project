package com.example.ap;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Teacher;

public class TeacherAccountInfoPane extends FragmentPane {
    public Label nameLabel;
    public Label nationalCodeLabel;
    public Label teacherCodeLabel;
    public Label departmentLabel;
    public Label enterYearLabel;
    public Label degreeLabel;
    public ImageView userImageView;
    public TextField emailField;
    public TextField phoneNumberField;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        nameLabel.setText("Name: " + teacher.name);
        nationalCodeLabel.setText("National Code: " + teacher.nationalCode);
        teacherCodeLabel.setText("Teacher Code: " + teacher.id);
        departmentLabel.setText("Department of " + teacher.department.name);
        enterYearLabel.setText("Entered " + teacher.enterYear);
        degreeLabel.setText("Degree " + teacher.degree.name());
        emailField.setText(teacher.email);
        phoneNumberField.setText(teacher.phoneNumber);
        userImageView.setImage(new Image("profile.png", 200, 200, false, true));
    }

    public void save() {
        teacher.email = emailField.getText();
        teacher.phoneNumber = phoneNumberField.getText();
    }
}
