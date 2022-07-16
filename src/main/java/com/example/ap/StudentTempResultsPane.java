package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Pair;
import logic.*;

import java.util.Optional;

public class StudentTempResultsPane extends FragmentPane {
    public ListView<FinalMark> listView;

    public void load(Student student) {
        this.student = student;
        ObservableList<FinalMark> items = FXCollections.observableArrayList();
        for (Course course : student.courseList) {
            for (FinalMark mark : course.finalMarks) {
                if (mark.student == student && !mark.isFinal) {
                    items.add(mark);
                    break;
                }
            }
        }

        listView.setItems(items);
        listView.setPadding(new Insets(0));
        listView.setCellFactory(finalMarkListView -> new MyCell(student));
    }

    public static class MyCell extends CourseResultCell {
        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(FinalMark finalMark, boolean b) {
            super.updateItem(finalMark, b);
            if (finalMark == null || b) {
                setGraphic(null);
            } else {
                courseNameLabel.setText(finalMark.course.name);
                markField.setText(finalMark.value + "");
                markField.setEditable(false);
                objectButton.setOnAction(actionEvent -> {
                    StudentObjectionDialog dialog = new StudentObjectionDialog(finalMark.objection);
                    Optional<Pair<String, ButtonType>> result = dialog.showAndWait();
                    if (result.get().getValue() == ButtonType.APPLY) {
                        finalMark.setObjection(new Objection(finalMark, result.get().getKey()));
                    }
                });
                setGraphic(root);
            }
        }
    }
}
