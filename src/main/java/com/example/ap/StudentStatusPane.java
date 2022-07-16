package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import logic.Course;
import logic.Student;
import logic.User;

import java.io.IOException;

public class StudentStatusPane extends FragmentPane {
    public ListView<Course> listView;
    public Label averageLabel;
    public Label unitsLabel;

    public void load(Student student) {
        this.student = student;
        ObservableList<Course> items = FXCollections.observableArrayList(student.courseList);
        listView.setItems(items);
        listView.setPadding(new Insets(0));
        listView.setCellFactory(courseListView -> new MyListCell(student));

        averageLabel.setText("Average: " + student.getAverage());
        unitsLabel.setText("Units Passed: " + student.getUnitSum());
    }


    public class MyListCell extends CourseCell {
        public MyListCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Course course, boolean b) {
            super.updateItem(course, b);
            if (course != null) {
                detButton.setOnAction(new StudentCourseListPane.MyListCell.OnDetailsButtonClicked(course, student));
            }
        }

        public class OnDetailsButtonClicked implements EventHandler<ActionEvent> {
            private Course course;
            private Student student;

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
