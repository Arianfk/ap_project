package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.transform.NonInvertibleTransformException;
import logic.Course;
import logic.Session;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EditSessionsDialog extends Dialog<ButtonType> {
    public ListView<Session> listView;
    public Button removeButton;
    public ComboBox<DayOfWeek> dayCombo;
    public TextField hourField;
    public TextField minuteField;
    public TextField lengthField;
    public Button addButton;
    public Course course;

    public EditSessionsDialog() {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("edit_sessions_dialog.fxml"));
        loader.setController(this);
        try {
            getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dayCombo.setItems(FXCollections.observableArrayList(DayOfWeek.values()));
    }

    public void load(Course course) {
        this.course = course;
        listView.setItems(FXCollections.observableArrayList(course.sessionList));
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setCellFactory(sessionListView -> new MyCell());
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        if (dayCombo.getValue() == null)
            return;
        try {
            Session session = new Session(dayCombo.getValue(), LocalTime.of(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())),
                    Integer.parseInt(lengthField.getText()), course);

            course.addSession(session);
            load(course);
        } catch (NumberFormatException ignored) {

        }
    }

    public void removeButtonClicked(ActionEvent actionEvent) {
        if (listView.getSelectionModel().getSelectedItem() == null) return;
        listView.getSelectionModel().getSelectedItem().delete();
        load(course);
    }

    public static class MyCell extends ListCell<Session> {
        @Override
        protected void updateItem(Session session, boolean b) {
            super.updateItem(session, b);
            if (session != null) {
                LocalTime end = session.start.plusMinutes(session.lengthMinutes);
                setText(session.start.format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + end.format(DateTimeFormatter.ofPattern("HH:mm"))
                        + " " + session.day);
            }
        }
    }
}
