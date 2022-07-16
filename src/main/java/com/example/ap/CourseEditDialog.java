package com.example.ap;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.util.Callback;
import logic.Course;
import logic.Teacher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CourseEditDialog extends Dialog<ButtonType> implements Callback<ButtonType, ButtonType> {
    public Course course;
    public TextField nameField;
    public TextField unitField;
    public ComboBox<Teacher> teacherCombo;
    public DatePicker datePicker;
    public TextField hourField;
    public TextField minuteField;

    public CourseEditDialog() {

        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("course_edit_dialog.fxml"));
        loader.setController(this);
        try {
            getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        getDialogPane().getButtonTypes().add(new ButtonType("Delete", ButtonBar.ButtonData.LEFT));

        teacherCombo.setItems(FXCollections.observableArrayList(Teacher.allTeachers));
    }

    public void load(Course course) {
        this.course = course;
        setResultConverter(this);
        nameField.setText(course.name);
        unitField.setText(course.unitCount + "");
        teacherCombo.setValue(course.teacher);
        if (course.finalExam.date != null) {
            datePicker.setValue(course.finalExam.date.toLocalDate());
            hourField.setText(course.finalExam.date.getHour() + "");
            minuteField.setText(course.finalExam.date.getMinute() + "");
        }
    }

    @Override
    public ButtonType call(ButtonType buttonType) {
        if (buttonType == ButtonType.APPLY) {
            course.setName(nameField.getText());
            course.setUnitCount(Integer.parseInt(unitField.getText()));
            course.setTeacher(teacherCombo.getValue());
            try {
                course.setFinalExamDate(LocalDateTime.of(datePicker.getValue(),
                        LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText()))));
            } catch (NumberFormatException ignored) {

            }
        } else if (buttonType.getText().equals("Delete")) course.delete();
        return buttonType;
    }
}
