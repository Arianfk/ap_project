package com.example.ap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import logic.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class StudentMinorRequestPane extends FragmentPane implements EventHandler<ActionEvent> {
    public ListView<Request> listView;
    public Button newButton;
    private Dialog<Department> dialog;

    public void load(Student student) {
        this.student = student;
        ObservableList<Request> items = FXCollections.observableArrayList(student.minorRequestList);
        listView.setItems(items);
        listView.setPadding(new Insets(0));
        listView.setCellFactory(requestListView -> new MyCell(student));

        newButton.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        if (student.getAverage() < 12) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your average is very low.");
            alert.showAndWait();
            return ;
        }
        dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("new_recommendation_request_dialog.fxml"));
        Node root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.getDialogPane().setContent(root);

        ListView<Department> listView = ((ListView<Department>) ((Pane) root).getChildren().get(0));
        listView.setPadding(new Insets(0));
        listView.setItems(FXCollections.observableArrayList(Department.allDepartments));
        listView.setCellFactory(departmentListView -> new MyDepCell());

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.CANCEL) return null;
            return dialog.getResult();
        });

        Optional<Department> tmp = dialog.showAndWait();
        if (tmp.isPresent()) {
            Department des = tmp.get();
            student.newMinorRequest(des);
            load(student);
        }
    }

    public static class MyCell extends RequestCell {
        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(Request request, boolean b) {
            super.updateItem(request, b);
            if (request != null) {
                MinorRequest minor = (MinorRequest) request;
                nameLabel.setText(minor.des.name);
                dateLabel.setText(minor.date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                detButton.setText(minor.status.name());
                detButton.setOnAction(actionEvent -> {
                    StudentMinorRequestDialog dialog = StudentMinorRequestDialog.createNew();
                    dialog.load((MinorRequest) request);
                    dialog.showAndWait();
                });
                setGraphic(root);
            } else {
                setGraphic(null);
            }
        }
    }

    public class MyDepCell extends DepartmentCell implements EventHandler<ActionEvent> {
        Department department;

        @Override
        protected void updateItem(Department department, boolean b) {
            super.updateItem(department, b);
            if (department != null) {
                label.setText(department.name);
                button.setText("Req");
                this.department = department;
                button.setOnAction(this);
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            dialog.setResult(department);
            dialog.close();
        }
    }
}
