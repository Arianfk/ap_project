package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import logic.Teacher;
import logic.User;

public class TeacherTeacherListPane extends FragmentPane {
    public ListView<Teacher> listView;
    public Button newTeacherButton;
    public Button changeAssistantButton;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        ObservableList<Teacher> items = FXCollections.observableArrayList();
        for (Teacher newTeacher : Teacher.allTeachers) {
            if (newTeacher.department == teacher.department) items.add(newTeacher);
        }
        listView.setItems(items);

        listView.setCellFactory(teacherListView -> new MyCell(teacher));

        newTeacherButton.setVisible(teacher.isAdmin());
        changeAssistantButton.setVisible(teacher.isAdmin());
    }

    public void newTeacherButtonClicked() {
        EditTeacherDialog dialog = new EditTeacherDialog();
        dialog.getDialogPane().getButtonTypes().remove(2);
        dialog.passwordField.setVisible(true);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (User.findByUsername(dialog.usernameField.getText()) != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Username is already used!");
                    alert.showAndWait();
                    return buttonType;
                }
                Teacher teacher = new Teacher(dialog.usernameField.getText(), dialog.passwordField.getText());
                teacher.setName(dialog.nameField.getText());
                teacher.setRoomId(dialog.roomField.getText());
                teacher.setDegree(dialog.degreeCombo.getValue());
                teacher.setDepartment(TeacherTeacherListPane.this.teacher.department);
            }
            return buttonType;
        });
        dialog.showAndWait();
        load(teacher);
    }

    public void changeAssistantClicked() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Pane pane = new Pane();

        ListView<Teacher> listView = new ListView<>();
        listView.setLayoutX(20);
        listView.setLayoutY(20);
        listView.setPadding(new Insets(0));
        listView.setPrefWidth(400);
        listView.setPrefHeight(350);

        for (Teacher teacher : Teacher.allTeachers) {
            if (teacher.department == this.teacher.department) listView.getItems().add(teacher);
        }

        listView.setCellFactory((teacherListView -> new AssistantCell(dialog, teacher)));

        pane.getChildren().add(listView);
        dialog.getDialogPane().setContent(pane);

        dialog.showAndWait();
    }

    public class AssistantCell extends TeacherCell implements EventHandler<ActionEvent> {
        Dialog<ButtonType> dialog;
        private Teacher teacher;

        public AssistantCell(Dialog<ButtonType> dialog, User user) {
            super(user);
            this.dialog = dialog;
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            teacher.department.setAssistant(teacher);
            this.dialog.close();
        }

        @Override
        protected void updateItem(Teacher teacher, boolean b) {
            super.updateItem(teacher, b);
            this.teacher = teacher;
            detButton.setText("select");
            detButton.setOnAction(this);
        }
    }

    public class MyCell extends TeacherCell implements EventHandler<ActionEvent> {
        public Button editButton;
        Teacher teacher;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Teacher teacher, boolean b) {
            super.updateItem(teacher, b);
            this.teacher = teacher;
            if (teacher != null) {
                if (TeacherTeacherListPane.this.teacher.isAdmin()) editButton.setVisible(true);
                editButton.setOnAction(this);
                detButton.setOnAction(this);
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == editButton) {
                EditTeacherDialog dialog = new EditTeacherDialog();
                dialog.load(teacher);
                dialog.setResultConverter(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        teacher.setName(dialog.nameField.getText());
                        teacher.setUsername(dialog.usernameField.getText());
                        teacher.setRoomId(dialog.roomField.getText());
                        teacher.setDegree(dialog.degreeCombo.getValue());
                    } else if (buttonType.getText().equals("Delete")) {
                        teacher.delete();
                    }
                    return buttonType;
                });
                dialog.showAndWait();
                load(TeacherTeacherListPane.this.teacher);
            } else {
                TeacherDetailsPane dialog = TeacherDetailsPane.getView();
                dialog.show(teacher);
            }
        }
    }
}
