package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import logic.QuitRequest;
import logic.ReqStatus;
import logic.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentQuitRequestPane extends FragmentPane {
    public Label stateLabel;
    public Label dateLabel;
    public Label reqStatusLabel;
    public Button requestButton;

    public void load(Student student) {
        this.student = student;
        if (student.quitRequest == null) {
            stateLabel.setText("No Request Yet");
            dateLabel.setText("");
            reqStatusLabel.setText("");
        } else {
            stateLabel.setText("");
            dateLabel.setText(student.quitRequest.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            reqStatusLabel.setText(student.quitRequest.status.name());
        }

        requestButton.setOnAction(actionEvent -> {
            if (student.quitRequest != null && student.quitRequest.status == ReqStatus.PROCESSING) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Another request is processing!");
                alert.showAndWait();
            } else {
                student.newQuitRequest();
                load(student);
            }
        });
    }
}
