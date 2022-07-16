package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import logic.*;

public class TeacherMinorRequestPane extends FragmentPane {
    public ListView<Request> listView;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.getItems().clear();
        for (MinorRequest request : teacher.department.minorRequestList) {
            if ((request.from.department == teacher.department) && (request.getHomeStatus() == ReqStatus.PROCESSING) || (request.from.department != teacher.department && request.getDesStatus() == ReqStatus.PROCESSING))
                listView.getItems().add(request);
        }

        listView.setCellFactory(requestListView -> new MyCell(teacher));
    }

    public class MyCell extends RequestCell implements EventHandler<ActionEvent> {
        MinorRequest request;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Request request, boolean b) {
            super.updateItem(request, b);
            if (request == null || b) {
                setGraphic(null);
            } else {
                this.request = (MinorRequest) request;
                setGraphic(root);
                nameLabel.setText(request.from.name);
                dateLabel.setText(request.from.studentNumber);
                detButton.setText("answer");
                detButton.setOnAction(this);
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            StudentMinorRequestDialog dialog = StudentMinorRequestDialog.createNew();
            dialog.setHeaderText(request.from.name);
            dialog.load(request);
            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, new ButtonType("Accept"), new ButtonType("Decline"));
            dialog.setResultConverter(new Callback<ButtonType, ButtonType>() {
                @Override
                public ButtonType call(ButtonType buttonType) {
                    if (buttonType == ButtonType.CANCEL) return buttonType;

                    ReqStatus status = null;
                    if (buttonType.getText().equals("Accept")) {
                        status = ReqStatus.ACCEPTED;
                    } else if (buttonType.getText().equals("Decline")) {
                        status = ReqStatus.DECLINED;
                    }

                    if (request.home == teacher.department) {
                        request.setHomeStatus(status);
                    } else {
                        request.setDesStatus(status);
                    }
                    request.answer();
                    return buttonType;
                }
            });
            dialog.showAndWait();
            load(teacher);
        }
    }
}
