package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import logic.*;

import java.util.Optional;

public class TeacherRecomRequestPane extends FragmentPane {
    public ListView<Request> listView;

    public void load(Teacher teacher) {
        this.teacher = teacher;
        listView.getItems().clear();
        this.teacher = teacher;
        listView.setPadding(new Insets(0));
        for (RecomRequest recomRequest : teacher.recomRequestList) {
            if (recomRequest.status == ReqStatus.PROCESSING) listView.getItems().add(recomRequest);
        }

        listView.setCellFactory(recomRequestListView -> new MyCell(teacher));
    }

    public class MyCell extends TeacherRequestCell implements EventHandler<ActionEvent> {
        RecomRequest request;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Request request, boolean b) {
            super.updateItem(request, b);
            this.request = (RecomRequest) request;
            acceptButton.setOnAction(this);
            declineButton.setOnAction(this);
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Pane pane = new Pane();
            TextField field = new TextField();
            field.setPromptText("Write Answer Here");
            field.setLayoutX(20);
            field.setLayoutY(20);
            field.setPrefHeight(400);
            field.setPrefWidth(500);
            field.setAlignment(Pos.TOP_LEFT);
            pane.getChildren().add(field);
            dialog.getDialogPane().setContent(pane);
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isEmpty() || result.get() == ButtonType.CANCEL)
                return;

            if (actionEvent.getSource() == acceptButton) {
                request.setStatus(ReqStatus.ACCEPTED);
            } else {
                request.setStatus(ReqStatus.DECLINED);
            }

            request.answer = field.getText();
            load(teacher);
        }
    }
}
