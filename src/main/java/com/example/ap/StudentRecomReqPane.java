package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logic.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class StudentRecomReqPane extends FragmentPane {
    public ListView<Request> listView;
    public Button newButton;

    public void load(Student student) {
        this.student = student;
        ObservableList<Request> items = FXCollections.observableArrayList(student.recomRequestList);
        listView.setItems(items);
        listView.setStyle("-fx-padding: 0px;");
        listView.setCellFactory(requestListView -> new MyCell(student));

        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Dialog<Teacher> dialog = new Dialog<>();
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

                FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("new_recommendation_request_dialog.fxml"));
                Node root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.getDialogPane().setContent(root);

                ListView listView = ((ListView) ((Pane) root).getChildren().get(0));
                listView.setItems(FXCollections.observableArrayList(Teacher.allTeachers));
                listView.setStyle("-fx-padding: 0px;");
                listView.setCellFactory((Callback<ListView<Teacher>, ListCell<Teacher>>) listView1 -> new MyTeacherCell(dialog, student));

                dialog.setResultConverter(new Callback<>() {
                    @Override
                    public Teacher call(ButtonType buttonType) {
                        if (buttonType == ButtonType.CANCEL) return null;
                        return dialog.getResult();
                    }
                });

                Optional<Teacher> tmp = dialog.showAndWait();
                if (tmp.isPresent()) {
                    Teacher teacher = tmp.get();
                    student.newRecommendationRequest(teacher);
                    load(student);
                }
            }

            class MyTeacherCell extends TeacherCell {
                Dialog<Teacher> dialog;

                public MyTeacherCell(Dialog<Teacher> dialog, User user) {
                    super(user);
                    this.dialog = dialog;
                }

                @Override
                protected void updateItem(Teacher teacher, boolean b) {
                    super.updateItem(teacher, b);
                    if (teacher != null) {
                        detButton.setText("Req");
                        detButton.setOnAction(actionEvent -> {
                            dialog.setResult(teacher);
                            dialog.close();
                        });
                    }
                }
            }
        });
    }

    public static class MyCell extends RequestCell {
        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Request request, boolean b) {
            super.updateItem(request, b);
            if (request != null) {
                setGraphic(root);
                nameLabel.setText(request.to.name);
                dateLabel.setText(request.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                detButton.setText(request.status.name());
                detButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        StudentRecomRequestDetDialog dialog = StudentRecomRequestDetDialog.createNew();
                        dialog.load((RecomRequest) request);
                        dialog.showAndWait();
                    }
                });
            } else {
                setGraphic(null);
            }
        }
    }
}
