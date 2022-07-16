package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import logic.Course;
import logic.Session;
import logic.Student;

import java.time.DayOfWeek;

public class WeekDaySchedulePane extends FragmentPane {
    public ListView<Session> sessionsListView;

    public void load(Student student, DayOfWeek day) {
        ObservableList<Session> items = FXCollections.observableArrayList();
        for (Course course : student.courseList) {
            for (Session session : course.sessionList) {
                if (session.day == day) items.add(session);
            }
        }

        sessionsListView.setItems(items);
    }
}
