package com.example.ap;

import javafx.scene.control.Label;
import logic.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentEduCertificatePane extends FragmentPane {

    public Label label;

    public void load(Student student) {
        label.setText("Student " + student.name + " with student id " + student.studentNumber + " is studying "
                + student.department.name + " in SUT.\n"
        + "This certificate is created in " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
    }
}
