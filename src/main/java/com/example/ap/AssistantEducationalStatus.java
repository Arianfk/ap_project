package com.example.ap;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import logic.Course;
import logic.FinalMark;
import logic.Teacher;
import logic.User;

public class AssistantEducationalStatus extends FragmentPane {
    public ListView<FinalMark> listView;
    public Label averageLabel;
    public Label unitLabel;
    public TextField nameField;
    public TextField numberField;

    private boolean isMarkValid(FinalMark mark) {
        return mark.student.name.startsWith(nameField.getText()) && mark.student.studentNumber.startsWith(numberField.getText());
    }

    public void load(Teacher teacher) {
        this.teacher = teacher;

        listView.setPadding(new Insets(0));
        listView.setCellFactory((finalMarkListView -> new MyCell(teacher)));

        listView.getItems().clear();

        int unitCount = 0;
        double sum = 0;
        for (Course course : teacher.department.courseList) {
            for (FinalMark mark : course.finalMarks) {
                if (mark.isFinal && isMarkValid(mark)) {
                    listView.getItems().add(mark);
                    if (mark.isPassed()) {
                        unitCount += mark.course.unitCount;
                        sum += mark.value * mark.course.unitCount;
                    }
                }
            }
        }

        averageLabel.setText(sum / unitCount + "");
        unitLabel.setText(unitCount + "");
    }

    @FXML
    public void filter() {
        load(teacher);
    }

    public static class MyCell extends StudentMarkCell {
        public MyCell(User user) {
            super(user);
        }

        @Override
        protected void updateItem(FinalMark finalMark, boolean b) {
            super.updateItem(finalMark, b);
            if (finalMark != null) {
                numberLabel.setText(finalMark.course.name + " - " + finalMark.course.teacher.name);
                markField.setText(finalMark.value + "");
                objectionButton.setVisible(false);
                markField.setEditable(false);
            }
        }
    }
}
