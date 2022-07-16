package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import logic.Course;
import logic.Student;
import logic.User;

import java.io.IOException;

public class StudentCourseListPane extends FragmentPane {
    public ListView<Course> courseListView;
    public TextField teacherFilterField;
    public TextField unitFilterField;
    public TextField idFilterFiled;

    public boolean isValidCourse(Course course) {
        return course.teacher.name.startsWith(teacherFilterField.getText()) && (unitFilterField.getText().equals("") || course.unitCount == Integer.parseInt(unitFilterField.getText())) && course.id.startsWith(idFilterFiled.getText());
    }

    public void load(Student student) {
        this.student = student;
        courseListView.getItems().clear();
        for (Course course : Course.allCourse) {
            if (isValidCourse(course)) courseListView.getItems().add(course);
        }
        courseListView.setCellFactory(courseListView -> {
            try {
                return new MyListCell(student);
            } catch (IOException ignored) {

            }
            return null;
        });
    }

    public void filter() {
        load(student);
    }

    public class MyListCell extends CourseCell {
        public MyListCell(User user) throws IOException {
            super(user);
        }

        @Override
        protected void updateItem(Course course, boolean b) {
            super.updateItem(course, b);
            if (course != null) {
                detButton.setOnAction(new OnDetailsButtonClicked(course, student));
            }
        }

        public static class OnDetailsButtonClicked implements EventHandler<ActionEvent> {
            private final Course course;
            private final Student student;

            public OnDetailsButtonClicked(Course course, Student student) {
                this.course = course;
                this.student = student;
            }

            @Override
            public void handle(ActionEvent actionEvent) {
                CourseDetailsPane pane = new CourseDetailsPane();
                pane.show(course, student);
            }
        }
    }
}
