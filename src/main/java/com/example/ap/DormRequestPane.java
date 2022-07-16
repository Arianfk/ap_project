package com.example.ap;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logic.DormRequest;
import logic.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DormRequestPane extends FragmentPane {
    public ListView<DormRequest> listView;
    public Button newButton;

    public void load(Student student) {
        listView.setItems(FXCollections.observableArrayList(student.dormRequestList));
        listView.setCellFactory(new Callback<ListView<DormRequest>, ListCell<DormRequest>>() {
            @Override
            public ListCell<DormRequest> call(ListView<DormRequest> dormRequestListView) {
                return new MyCell();
            }
        });

        newButton.setOnAction(actionEvent -> {
            student.dormRequest();
            load(student);
        });
    }

    public static class MyCell extends ListCell<DormRequest> {
        @Override
        protected void updateItem(DormRequest dormRequest, boolean b) {
            super.updateItem(dormRequest, b);
            setBackground(null);
            if (dormRequest == null || b) {
                setGraphic(null);
            } else {
                AnchorPane pane = new AnchorPane();

                Label state = new Label();
                state.setText(dormRequest.status.name());
                pane.getChildren().add(state);

                Label date = new Label();
                date.setLayoutX(150);
                date.setText(dormRequest.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                pane.getChildren().add(date);

                setGraphic(pane);
            }
        }
    }
}
