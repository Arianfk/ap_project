package com.example.ap;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaneMenuController {
    public ListView<String> listView;
    public AnchorPane mainPane;
    public List<FragmentPane> items = new ArrayList<>();

    public Node getView() {


        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("menu.fxml"));
        loader.setController(this);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    public void initialize() {
        ObservableList<String> names = listView.getItems();
        for (FragmentPane pane : items) {
            mainPane.getChildren().add(pane.root);
            pane.root.setVisible(false);
            names.add(pane.name);
        }

        listView.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            try {
                FragmentPane fragmentPane = items.get(listView.getSelectionModel().getSelectedIndex());
                fragmentPane.load(fragmentPane.student);
                fragmentPane.load(fragmentPane.teacher);
            } catch (IndexOutOfBoundsException ignored) {

            }
            for (int i = 0; i < names.size(); i++) {
                mainPane.getChildren().get(i).setVisible(names.get(i).equals(listView.getSelectionModel().getSelectedItem()));
            }
        });
    }
}
