package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import logic.*;

public class TeacherQuitRequest extends FragmentPane {
    public ListView<Request> listView;

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.getItems().clear();
        for (Student student : teacher.department.studentList) {
            if (student.quitRequest != null && student.quitRequest.status == ReqStatus.PROCESSING) {
                listView.getItems().add(student.quitRequest);
            }
        }

        listView.setCellFactory(requestListView -> new MyCell(teacher));
    }

    public class MyCell extends TeacherRequestCell implements EventHandler<ActionEvent> {
        QuitRequest request;

        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Request request, boolean b) {
            super.updateItem(request, b);
            this.request = (QuitRequest) request;
            if (request != null) {
                acceptButton.setOnAction(this);
                declineButton.setOnAction(this);
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (actionEvent.getSource() == acceptButton) {
                request.setStatus(ReqStatus.ACCEPTED);
            } else {
                request.setStatus(ReqStatus.DECLINED);
            }
            load(teacher);
        }
    }
}
