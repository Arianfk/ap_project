package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.AccessibleAction;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import logic.Course;
import logic.Teacher;
import logic.User;

public class CourseSummeryPane extends FragmentPane {
    public ListView<Course> listView;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.setCellFactory((courseListView -> new MyCell(teacher)));
        listView.getItems().clear();
        listView.getItems().addAll(teacher.department.courseList);
    }

    public static class MyCell extends CourseCell implements EventHandler<ActionEvent> {
        private Course course;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Course course, boolean b) {
            super.updateItem(course, b);
            this.course = course;
            if (course != null) {
                detButton.setOnAction(this);
                detButton.setText("summery");
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.setContentText("Number of Passed Students: " + course.getPassedStudentNumber()
            + "\nNumber of Failed Students: " + course.getFailedStudentNumber()
            + "\nTotal Average: " + course.getAverage()
            + "\nPassed Students Average: " + course.getPassedAverage());
            dialog.showAndWait();
        }
    }
}
