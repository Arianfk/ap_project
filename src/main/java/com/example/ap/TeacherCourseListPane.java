package com.example.ap;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.util.Callback;
import logic.Course;
import logic.Degree;
import logic.Teacher;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TeacherCourseListPane extends FragmentPane {
    public ListView<Course> listView;
    public Button newCourseButton;
    public Button editSessionsButton;
    public TextField unitField;
    public ComboBox<Degree> degreeCombo;
    public TextField nameField;

    public boolean isCourseValid(Course course) {
        return (unitField.getText().equals("") || course.unitCount == Integer.parseInt(unitField.getText())) &&
                course.teacher.name.startsWith(nameField.getText()) &&
                (degreeCombo.getValue() == null || degreeCombo.getValue() == course.degree);
    }

    public void load(Teacher teacher) {
        this.teacher = teacher;

        if (!teacher.isAssistant()) {
            newCourseButton.setVisible(false);
            editSessionsButton.setVisible(false);
        }
        listView.setPadding(new Insets(0));

        listView.getItems().clear();
        for (Course course : Course.allCourse) {
            if (!course.finished && isCourseValid(course)) {
                listView.getItems().add(course);
            }
        }

        listView.setCellFactory(courseListView -> {
            FXMLLoader loader = new FXMLLoader(MyApplication.class.getResource("teacher_course_cell.fxml"));
            TeacherCourseCell cell = new TeacherCourseCell();
            loader.setController(cell);
            Node root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            cell.root = root;
            return cell;
        });
    }

    @FXML
    public void initialize() {
        degreeCombo.setItems(FXCollections.observableArrayList(Degree.values()));
    }

    public void onNewCourseButtonClicked() {
        CourseEditDialog dialog = new CourseEditDialog();
        dialog.getDialogPane().getButtonTypes().remove(2);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.APPLY && dialog.teacherCombo.getValue() != null) {
                Course course = new Course(dialog.nameField.getText(), dialog.teacherCombo.getValue());
                if (!dialog.unitField.getText().isEmpty())
                    course.unitCount = Integer.parseInt(dialog.unitField.getText());
                course.setDepartment(teacher.department);
                try {
                    course.finalExam.date = LocalDateTime.of(dialog.datePicker.getValue(),
                            LocalTime.of(Integer.parseInt(dialog.hourField.getText()), Integer.parseInt(dialog.minuteField.getText())));
                } catch (NumberFormatException ignored) {

                }
                teacher.department.courseList.add(course);
            }
            return buttonType;
        });

        dialog.showAndWait();
        load(teacher);
    }

    public void editSessionsButtonClicked() {
        if (listView.getSelectionModel().getSelectedItem() == null)
            return;
        EditSessionsDialog dialog = new EditSessionsDialog();
        dialog.load(listView.getSelectionModel().getSelectedItem());
        dialog.showAndWait();
    }

    public void filter() {
        load(teacher);
    }

    public void clear() {
        degreeCombo.getSelectionModel().clearSelection();
        nameField.setText("");
        unitField.setText("");
        load(teacher);
    }

    public class TeacherCourseCell extends ListCell<Course> {
        public Node root;
        public Label courseNameLabel;
        public Label teacherNameLabel;
        public Button infoButton;
        public Button editButton;
        private Course course;

        @Override
        protected void updateItem(Course course, boolean b) {
            super.updateItem(course, b);
            this.course = course;
            setPadding(new Insets(0));
            if (b || course == null) {
                setBackground(null);
                setGraphic(null);
            } else {
                courseNameLabel.setText(course.name);
                teacherNameLabel.setText(course.teacher.name);
                editButton.setVisible(teacher.isAssistant() && course.department == teacher.department);
                setGraphic(root);
            }
        }

        @Override
        public void updateSelected(boolean b) {
            super.updateSelected(b);
            if (b) {
                setBackground(new Background(new BackgroundFill(teacher.theme.listViewSelectionColor, new CornerRadii(10), new Insets(5))));
            } else {
                setBackground(null);
            }
        }

        public void onInfoButtonClicked(ActionEvent actionEvent) {
            if (course == null) return;

            CourseDetailsPane dialog = new CourseDetailsPane();
            dialog.show(course);
        }

        public void onEditButtonClicked(ActionEvent actionEvent) {
            if (course == null) return;

            CourseEditDialog dialog = new CourseEditDialog();
            dialog.load(course);
            dialog.showAndWait();
            load(teacher);
        }
    }
}
