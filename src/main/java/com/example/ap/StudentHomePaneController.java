package com.example.ap;

import javafx.scene.control.Label;
import logic.Student;

import java.time.format.DateTimeFormatter;

public class StudentHomePaneController extends FragmentPane {

    public Label statusLabel;
    public Label guidNameLabel;
    public Label regCertificateLabel;
    public Label regTimeLabel;

    public void load(Student student) {
        this.student = student;
        this.statusLabel.setText("State: " + student.isRegistrationAllowed);
        this.guidNameLabel.setText("Guide Name: " + student.guide.name);
        this.regCertificateLabel.setText("Registration Certificate: " + student.registrationCertificate);
        this.regTimeLabel.setText("Registration Time: " + student.registerTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
    }
}
