package com.example.ap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import logic.User;

public class ChangeThemePane extends FragmentPane {
    public ComboBox<ColorTypes> menuBackgroundColorCombo;
    public ComboBox<ColorTypes> listsSelectionColorCombo;
    public ComboBox<ColorTypes> backgroundColorCombo;

    public void load(User user) {
        ObservableList<ColorTypes> items = FXCollections.observableArrayList(ColorTypes.values());
        menuBackgroundColorCombo.setItems(items);
        menuBackgroundColorCombo.setValue(ColorTypes.find(user.theme.menuBarBackgroundColor));
        menuBackgroundColorCombo.valueProperty().addListener((observableValue, colorTypes, t1) -> user.theme.menuBarBackgroundColor = t1.color);

        listsSelectionColorCombo.setItems(items);
        listsSelectionColorCombo.setValue(ColorTypes.find(user.theme.listViewSelectionColor));
        listsSelectionColorCombo.valueProperty().addListener(((observableValue, colorTypes, t1) -> user.theme.listViewSelectionColor = t1.color));

        backgroundColorCombo.setItems(items);
        backgroundColorCombo.setValue(ColorTypes.find(user.theme.backgroundColor));
        backgroundColorCombo.valueProperty().addListener((observableValue, colorTypes, t1) -> user.theme.backgroundColor = t1.color);
    }
}
