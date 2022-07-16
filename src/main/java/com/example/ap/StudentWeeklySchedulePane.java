package com.example.ap;

import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import logic.Course;
import logic.Session;
import logic.Student;
import logic.Teacher;

import java.time.DayOfWeek;

public class StudentWeeklySchedulePane extends FragmentPane {
    public TabPane tabPane;

    public void load(Student student) {
        if (student == null)
            return ;
        tabPane.getTabs().clear();
        this.student = student;
        for (DayOfWeek day : DayOfWeek.values()) {
            Tab tab = new Tab(day.name());
            tabPane.getTabs().add(tab);
            tab.setContent(FragmentPane.getView("week_day_schedule_pane.fxml").root);
        }

        for (Course course : student.courseList) {
            for (Session session : course.sessionList) {
                ((ListView<Session>) ((AnchorPane) tabPane.getTabs().get(session.day.getValue() - 1).getContent()).getChildren().get(0)).getItems().add(session);
            }
        }
    }

    public void load(Teacher teacher) {
        if (teacher == null)
            return ;
        tabPane.getTabs().clear();
        this.teacher = teacher;
        for (DayOfWeek day : DayOfWeek.values()) {
            Tab tab = new Tab(day.name());
            tabPane.getTabs().add(tab);
            tab.setContent(FragmentPane.getView("week_day_schedule_pane.fxml").root);
        }

        for (Course course : teacher.department.courseList) {
            if (course.teacher == teacher) {
                for (Session session : course.sessionList) {
                    ((ListView<Session>) ((AnchorPane) tabPane.getTabs().get(session.day.getValue() - 1).getContent()).getChildren().get(0)).getItems().add(session);
                }
            }
        }
    }
}
