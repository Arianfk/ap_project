package com.example.ap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import logic.Degree;
import logic.Student;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentRequestsPane extends FragmentPane {
    public TabPane tabPane;

    public void load(Student student) {
        this.student = student;
        tabPane.getTabs().clear();
        if (student.degree != Degree.PHD) {
            Tab recomReq = new Tab("Recommendation Request");
            StudentRecomReqPane recomReqPane = (StudentRecomReqPane) FragmentPane.getView("student_recom_req_pane.fxml");
            recomReqPane.load(student);
            recomReq.setContent(recomReqPane.root);
            tabPane.getTabs().add(recomReq);
        }

        Tab eduCer = new Tab("Educational Certificate");
        StudentEduCertificatePane eduCertificatePane = (StudentEduCertificatePane) FragmentPane.getView("student_edu_certificate_pane.fxml");
        eduCertificatePane.load(student);
        eduCer.setContent(eduCertificatePane.root);
        tabPane.getTabs().add(eduCer);

        if (student.degree == Degree.BACHELOR) {
            Tab minorRequest = new Tab("Minor Request");
            StudentMinorRequestPane minorRequestPane = (StudentMinorRequestPane) FragmentPane.getView("student_minor_request_pane.fxml");
            minorRequestPane.load(student);
            minorRequest.setContent(minorRequestPane.root);
            tabPane.getTabs().add(minorRequest);
        } else if (student.degree == Degree.MASTERSHIP) {
            Tab dormRequestTab = new Tab("Dorm Request");
            DormRequestPane dormRequestPane = (DormRequestPane) FragmentPane.getView("dorm_request_pane.fxml");
            dormRequestPane.load(student);
            dormRequestTab.setContent(dormRequestPane.root);
            tabPane.getTabs().add(dormRequestTab);
        } else {
            Tab thesisTab = new Tab("Thesis Time");

            AnchorPane pane = new AnchorPane();

            Label dateLabel = new Label();
            if (student.thesisDate == null) {
                dateLabel.setText("No Thesis Date Yet");
            } else {
                dateLabel.setText("Your Thesis Defence is in " + student.thesisDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
            }
            dateLabel.setLayoutX(2);
            dateLabel.setLayoutY(2);
            pane.getChildren().add(dateLabel);

            Button newButton = new Button("New Time");
            pane.getChildren().add(newButton);
            newButton.setLayoutX(2);
            newButton.setLayoutY(20);
            newButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    int next = (int) Math.floor(Math.random() * 10);
                    student.thesisDate = LocalDateTime.now().plusDays(next);
                    dateLabel.setText("Your Thesis Defence is in " + student.thesisDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
                }
            });

            thesisTab.setContent(pane);
            tabPane.getTabs().add(thesisTab);
        }

        Tab quitRequest = new Tab("Quit Request");
        StudentQuitRequestPane quitRequestPane = (StudentQuitRequestPane) FragmentPane.getView("student_quit_request_pane.fxml");
        quitRequestPane.load(student);
        quitRequest.setContent(quitRequestPane.root);
        tabPane.getTabs().add(quitRequest);
    }
}
