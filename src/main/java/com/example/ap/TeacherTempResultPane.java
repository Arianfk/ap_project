package com.example.ap;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherTempResultPane extends FragmentPane {
    public ListView<Course> listView;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.getItems().clear();
        for (Course course : teacher.department.courseList) {
            if (course.teacher == teacher && !course.isFinished()) {
                listView.getItems().add(course);
            }
        }

        listView.setCellFactory(courseListView -> new MyCell(teacher));
    }

    public class MyCell extends CourseCell implements EventHandler<ActionEvent> {
        public Course course;
        public List<TextField> fieldList;
        public Button finalButton;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Course course, boolean b) {
            super.updateItem(course, b);
            this.course = course;
            if (course != null) {
                detButton.setOnAction(this);
                detButton.setText("marks");
                if (course.isTempResultSet()) finalButton.setVisible(true);
                finalButton.setText("make final");
                finalButton.setOnAction(actionEvent -> {
                    course.finish();
                    load(teacher);
                });
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.APPLY);

            ListView<FinalMark> listView = new ListView<>();
            listView.prefHeight(360);
            listView.setPrefWidth(400);
            listView.setPadding(new Insets(0));
            listView.setLayoutX(20);
            listView.setLayoutY(20);

            Pane pane = new Pane();
            pane.getChildren().add(listView);
            dialog.getDialogPane().setContent(pane);
            List<FinalMark> tempMarks = new ArrayList<>();
            if (course.isTempResultSet()) {
                for (FinalMark mark : course.finalMarks) {
                    FinalMark temp = new FinalMark(mark.student, mark.course);
                    temp.value = mark.value;
                    temp.objection = mark.objection;
                    tempMarks.add(temp);
                }
            } else {
                for (Student student : course.studentList) {
                    tempMarks.add(new FinalMark(student, course));
                }
            }

            listView.setItems(FXCollections.observableArrayList(tempMarks));

            fieldList = new ArrayList<>();
            for (FinalMark ignored : tempMarks)
                fieldList.add(null);

            listView.setCellFactory(finalMarkListView -> new MarkCell(teacher));

            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.APPLY) {
                    boolean flag = true;
                    for (int i = 0; i < fieldList.size(); i++) {
                        TextField field = fieldList.get(i);
                        try {
                            double val = Double.parseDouble(field.getText());
                            tempMarks.get(i).setValue(val);
                            if (0 > val || val > 20) flag = false;
                        } catch (NumberFormatException ignored) {
                            flag = false;
                        }
                    }

                    if (flag) {
                        course.setFinalMarks(tempMarks);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Not Correct Format");
                        alert.showAndWait();
                    }
                }
                return buttonType;
            });

            dialog.showAndWait();
            load(teacher);
        }


        public class MarkCell extends StudentMarkCell implements EventHandler<ActionEvent> {
            FinalMark mark;

            public MarkCell(User user) {
                super(user);
            }

            @Override
            protected void updateItem(FinalMark finalMark, boolean b) {
                super.updateItem(finalMark, b);
                this.mark = finalMark;
                if (finalMark != null) {
                    fieldList.set(getIndex(), markField);
                    objectionButton.setOnAction(this);
                    if (course.isTempResultSet()) {
                        markField.setText(finalMark.value + "");
                    } else {
                        markField.setText("");
                    }
                    objectionButton.setVisible(finalMark.objection != null);
                }
            }

            @Override
            public void handle(ActionEvent actionEvent) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("objection_answer_dialog.fxml"));
                try {
                    dialog.getDialogPane().setContent(loader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ((Label) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(0)).setText(mark.objection.object);
                ((TextField) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(1)).setText(mark.objection.ans);
                dialog.setResultConverter(buttonType -> {
                    if (buttonType == ButtonType.APPLY) {
                        mark.objection.answer(((TextField) ((AnchorPane) dialog.getDialogPane().getContent()).getChildren().get(1)).getText());
                    }
                    return buttonType;
                });
                dialog.showAndWait();
            }
        }
    }
}
