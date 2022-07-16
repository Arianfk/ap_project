package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import logic.Course;
import logic.Student;
import logic.Teacher;
import logic.User;

public class StudentTeacherListPane extends FragmentPane {
    public ListView<Teacher> teacherListView;

    public void load(Student student) {
        this.student = student;
        ObservableList<Teacher> items = FXCollections.observableArrayList();
        for (Course course : student.courseList) {
            if (!items.contains(course.teacher)) {
                items.add(course.teacher);
            }
        }


        teacherListView.setItems(items);

        teacherListView.setCellFactory(teacherListView -> new MyCell(student));
    }

    static class OnDetailsButtonClick implements EventHandler<ActionEvent> {
        private Teacher teacher;

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            TeacherDetailsPane pane = TeacherDetailsPane.getView();
            pane.show(teacher);
        }
    }

    static class MyCell extends TeacherCell {
        public MyCell(User user) {
            super(user);
            detButton.setOnAction(new OnDetailsButtonClick());
        }

        @Override
        protected void updateItem(Teacher teacher, boolean b) {
            super.updateItem(teacher, b);
            if (teacher != null) ((OnDetailsButtonClick) detButton.getOnAction()).setTeacher(teacher);
        }
    }
}
