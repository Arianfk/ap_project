package com.example.ap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import logic.Course;
import logic.FinalMark;
import logic.Teacher;
import logic.User;

import java.io.IOException;

public class AssistantMarksPane extends FragmentPane {
    public ListView<FinalMark> listView;
    public TextField courseField;
    public TextField studentField;
    public TextField teacherField;

    private boolean isValidMark(FinalMark mark) {
        return mark.course.name.startsWith(courseField.getText()) && mark.student.name.startsWith(studentField.getText()) && mark.course.teacher.name.startsWith(teacherField.getText());
    }

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.getItems().clear();
        for (Course course : teacher.department.courseList) {
            for (FinalMark mark : course.finalMarks) {
                if (isValidMark(mark)) listView.getItems().add(mark);
            }
        }

        listView.setCellFactory((finalMarkListView -> new MyCell(teacher)));
    }

    @FXML
    public void filter() {
        load(teacher);
    }

    public static class MyCell extends StudentMarkCell {
        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(FinalMark finalMark, boolean b) {
            super.updateItem(finalMark, b);
            if (finalMark != null) {
                numberLabel.setText(finalMark.course.name + " - " + finalMark.course.teacher.name);
                objectionButton.setVisible(finalMark.objection != null);
                objectionButton.setOnAction(actionEvent -> {
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

                    FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("objection_answer_dialog.fxml"));
                    try {
                        dialog.getDialogPane().setContent(loader.load());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ((Label) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(0)).setText(finalMark.objection.object);
                    ((TextField) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(1)).setText(finalMark.objection.ans);
                    ((TextField) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(1)).setEditable(false);
                    dialog.showAndWait();
                });
            }
        }
    }
}
