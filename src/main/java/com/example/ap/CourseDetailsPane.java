package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import logic.Course;
import logic.FinalMark;
import logic.Student;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CourseDetailsPane extends Dialog<ButtonType> {
    public Label courseNameLabel;
    public Label courseTeacherNameLabel;
    public Label courseUnitLabel;
    public Label courseDepartmentLabel;
    public Label courseIDLabel;
    public Label finalMarkLabel;
    public ListView<String> listView;
    public Label examTime;

    public CourseDetailsPane() {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("course_details.fxml"));
        loader.setController(this);
        try {
            getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
    }

    public void show(Course course, Student student) {
        listView.setVisible(false);
        courseNameLabel.setText(courseNameLabel.getText() + course.name);
        courseTeacherNameLabel.setText(courseTeacherNameLabel.getText() + course.teacher.name);
        courseIDLabel.setText(courseIDLabel.getText() + course.id);
        courseUnitLabel.setText(courseUnitLabel.getText() + course.unitCount);
        courseDepartmentLabel.setText(courseDepartmentLabel.getText() + course.department.name);
        FinalMark mark = course.getFinalMark(student);
        finalMarkLabel.setText(finalMarkLabel.getText() + ((mark == null || !mark.isFinal) ? "N/A" : "" + mark.value));
        if (course.finalExam.date != null)
            examTime.setText(examTime.getText() + course.finalExam.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        showAndWait();
    }

    public void show(Course course) {
        finalMarkLabel.setVisible(false);
        courseNameLabel.setText(courseNameLabel.getText() + course.name);
        courseTeacherNameLabel.setText(courseTeacherNameLabel.getText() + course.teacher.name);
        courseIDLabel.setText(courseIDLabel.getText() + course.id);
        courseUnitLabel.setText(courseUnitLabel.getText() + course.unitCount);
        courseDepartmentLabel.setText(courseDepartmentLabel.getText() + course.department.name);
        for (Student student : course.studentList) {
            listView.getItems().add(student.name);
        }
        showAndWait();
    }
}
