package com.example.ap;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import logic.Department;

import java.io.IOException;

public class DepartmentCell extends ListCell<Department> {
    public Node root;
    public Label label;
    public Button button;

    public DepartmentCell() {
        FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("department_cell.fxml"));
        loader.setController(this);
        try {
            this.root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBackground(null);
        setPadding(new Insets(0));
    }

    @Override
    protected void updateItem(Department department, boolean b) {
        super.updateItem(department, b);
        if (department == null || b) {
            setGraphic(null);
        } else {
            setGraphic(root);
        }
    }
}
