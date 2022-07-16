package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import logic.Course;
import logic.Exam;
import logic.Student;
import logic.Teacher;

public class StudentExamListPane extends FragmentPane {
    public ListView<Exam> examListView;

    public void load(Student student) {
        this.student = student;
        if (student == null) return;

        ObservableList<Exam> items = FXCollections.observableArrayList();
        for (Course course : student.courseList) {
            items.addAll(course.examList);
            if (course.finalExam != null && course.finalExam.date != null) items.add(course.finalExam);
        }


        FXCollections.sort(items, (o1, o2) -> {
            if (o1.date.equals(o2.date)) return 0;
            return (o1.date.isBefore(o2.date) ? 1 : -1);
        });
        examListView.setItems(items);
    }

    @Override
    public void load(Teacher teacher) {
        this.teacher = teacher;
        if (teacher == null) return;

        ObservableList<Exam> items = FXCollections.observableArrayList();
        for (Course course : teacher.department.courseList) {
            if (course.teacher == teacher) {
                items.addAll(course.examList);
                if (course.finalExam != null && course.finalExam.date != null) items.add(course.finalExam);
            }
        }


        FXCollections.sort(items, (o1, o2) -> {
            if (o1.date.equals(o2.date)) return 0;
            return (o1.date.isBefore(o2.date) ? 1 : -1);
        });
        examListView.setItems(items);
    }
}
