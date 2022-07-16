package com.example.ap;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import logic.Student;
import logic.Teacher;
import logic.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPageViewController {
    public static Theme theme;
    static Logger logger = LogManager.getLogger(MainPageViewController.class.getName());
    public ImageView userImageView;
    public Label userNameLabel;
    public Label userEmailLabel;
    public Label userLastLogInLabel;
    public Label timeLabel;
    public TabPane tabPane;
    public AnchorPane menu;
    MyApplication context;
    User user;

    public void init(MyApplication context, User user) {
        logger.info("User " + user.username + " logged in");
        tabPane.getTabs().clear();

        this.context = context;
        this.user = user;
        theme = user.theme;

        menu.setBackground(new Background(new BackgroundFill(user.theme.menuBarBackgroundColor, CornerRadii.EMPTY, new Insets(0))));

        timeLabel.setText("Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss")));
        userLastLogInLabel.setText("Last Log In: " + user.lastLogIn.format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
        userNameLabel.setText(user.name);
        userEmailLabel.setText(user.email);
        userImageView.setImage(new Image("profile.png", 70, 70, false, true));
        if (user.imagePath != null) userImageView.setImage(new Image(user.imagePath));

        if (user instanceof Student) {
            // ********** Home Tab **********
            Tab homeTab = new Tab("Home");
            tabPane.getTabs().add(homeTab);
            StudentHomePaneController homePane = (StudentHomePaneController) FragmentPane.getView("student_home_view.fxml");
            homePane.load((Student) user);
            homeTab.setContent(homePane.root);

            homeTab.setOnSelectionChanged(event -> homePane.load((Student) user));
            // *********** Home Tab End ************

            // ************ Register Tab **************
            Tab registrationTab = new Tab("Registration");
            tabPane.getTabs().add(registrationTab);
            PaneMenuController registrationTabMenu = new PaneMenuController();

            StudentCourseListPane courseListPane = (StudentCourseListPane) FragmentPane.getView("student_course_list_view.fxml");
            courseListPane.setName("My Courses");
            courseListPane.load((Student) user);
            registrationTabMenu.items.add(courseListPane);

            StudentTeacherListPane teacherListPane = (StudentTeacherListPane) FragmentPane.getView("student_teacher_list_pane.fxml");
            teacherListPane.setName("My Teachers");
            teacherListPane.load((Student) user);
            registrationTabMenu.items.add(teacherListPane);

            registrationTab.setContent(registrationTabMenu.getView());
            registrationTab.setOnSelectionChanged(event -> registrationTabMenu.listView.getSelectionModel().clearSelection());
            // ************* Register Tab End *******************

            // ************* Educational Services Tab ******************
            Tab eduSerTab = new Tab("Educational Services");
            tabPane.getTabs().add(eduSerTab);
            PaneMenuController eduSerTabMenu = new PaneMenuController();

            StudentWeeklySchedulePane schedulePane = (StudentWeeklySchedulePane) FragmentPane.getView("student_weekly_schedule_pane.fxml");
            schedulePane.setName("Weekly Schedule");
            schedulePane.load((Student) user);
            eduSerTabMenu.items.add(schedulePane);

            StudentExamListPane examListPane = (StudentExamListPane) FragmentPane.getView("student_exam_list_pane.fxml");
            examListPane.setName("My Exams");
            examListPane.load((Student) user);
            eduSerTabMenu.items.add(examListPane);

            StudentRequestsPane requestsPane = (StudentRequestsPane) FragmentPane.getView("student_requests_pane.fxml");
            requestsPane.setName("Requests");
            requestsPane.load((Student) user);
            eduSerTabMenu.items.add(requestsPane);

            eduSerTab.setContent(eduSerTabMenu.getView());
            eduSerTab.setOnSelectionChanged(event -> eduSerTabMenu.listView.getSelectionModel().clearSelection());
            // ************** Educational Services Tab End ******************

            // ************** Results Tab *****************
            Tab resultsTab = new Tab("Results");
            tabPane.getTabs().add(resultsTab);
            PaneMenuController resultsTabMenu = new PaneMenuController();

            StudentTempResultsPane tempResultsPane = (StudentTempResultsPane) FragmentPane.getView("student_temp_results_pane.fxml");
            tempResultsPane.setName("Temp Results");
            tempResultsPane.load((Student) user);
            resultsTabMenu.items.add(tempResultsPane);

            StudentStatusPane statusPane = (StudentStatusPane) FragmentPane.getView("student_status_pane.fxml");
            statusPane.setName("Status");
            statusPane.load((Student) user);
            resultsTabMenu.items.add(statusPane);

            resultsTab.setContent(resultsTabMenu.getView());
            resultsTab.setOnSelectionChanged(event -> resultsTabMenu.listView.getSelectionModel().clearSelection());

            // ************** Results Tab End ****************

            // **************** Account Info Tab **************
            Tab accountInfoTab = new Tab("Account");
            tabPane.getTabs().add(accountInfoTab);

            StudentAccountInfoPane accountInfoPane = (StudentAccountInfoPane) FragmentPane.getView("student_account_info_pane.fxml");
            accountInfoPane.load((Student) user);

            accountInfoTab.setContent(accountInfoPane.root);
            accountInfoTab.setOnSelectionChanged(event -> accountInfoPane.load((Student) user));
            // ************** Account Info Tab End ***************
        } else {
            // Teacher

            // ******* Registration *************
            Tab registrationTab = new Tab("Registration");

            PaneMenuController registrationMenu = new PaneMenuController();

            TeacherCourseListPane courseListPane = (TeacherCourseListPane) FragmentPane.getView("teacher_course_list_pane.fxml");
            courseListPane.setName("Course List");
            courseListPane.load((Teacher) user);
            registrationMenu.items.add(courseListPane);

            TeacherTeacherListPane teacherListPane = (TeacherTeacherListPane) FragmentPane.getView("teacher_teacher_list_pane.fxml");
            teacherListPane.setName("Teacher List");
            teacherListPane.load((Teacher) user);
            registrationMenu.items.add(teacherListPane);

            registrationTab.setContent(registrationMenu.getView());
            tabPane.getTabs().add(registrationTab);
            registrationTab.setOnSelectionChanged(event -> registrationMenu.listView.getSelectionModel().clearSelection());

            // ******** Registration End ***************

            // *************** Educational Services ************************
            Tab eduSerTab = new Tab("Educational Services");
            PaneMenuController eduSerMenu = new PaneMenuController();

            TeacherRecomRequestPane recomRequestPane = (TeacherRecomRequestPane) FragmentPane.getView("teacher_recom_request_pane.fxml");
            recomRequestPane.setName("Recommendation Requests");
            recomRequestPane.load((Teacher) user);
            eduSerMenu.items.add(recomRequestPane);

            StudentWeeklySchedulePane schedulePane = (StudentWeeklySchedulePane) FragmentPane.getView("student_weekly_schedule_pane.fxml");
            schedulePane.setName("Weekly Schedule");
            schedulePane.load((Teacher) user);
            eduSerMenu.items.add(schedulePane);

            if (((Teacher) user).isAssistant()) {
                TeacherMinorRequestPane minorPane = (TeacherMinorRequestPane) FragmentPane.getView("teacher_minor_request_pane.fxml");
                minorPane.setName("Minor Requests");
                minorPane.load((Teacher) user);
                eduSerMenu.items.add(minorPane);

                TeacherQuitRequest quitPane = (TeacherQuitRequest) FragmentPane.getView("teacher_quit_request.fxml");
                quitPane.setName("Quit Requests");
                quitPane.load((Teacher) user);
                eduSerMenu.items.add(quitPane);
            }

            StudentExamListPane examListPane = (StudentExamListPane) FragmentPane.getView("student_exam_list_pane.fxml");
            examListPane.setName("Exam List");
            examListPane.load((Teacher) user);
            eduSerMenu.items.add(examListPane);

            eduSerTab.setContent(eduSerMenu.getView());
            tabPane.getTabs().add(eduSerTab);
            eduSerTab.setOnSelectionChanged(event -> eduSerMenu.listView.getSelectionModel().clearSelection());
            // ******************* Educational Services End *******************

            // *********************** Results Tab *******************
            Tab resultsTab = new Tab("Results");
            PaneMenuController resultsMenu = new PaneMenuController();

            TeacherTempResultPane tempResultPane = (TeacherTempResultPane) FragmentPane.getView("teacher_temp_result_pane.fxml");
            tempResultPane.setName("Temp Results");
            tempResultPane.load((Teacher) user);
            resultsMenu.items.add(tempResultPane);

            if (((Teacher) user).isAssistant()) {
                AssistantMarksPane assistantMarksPane = (AssistantMarksPane) FragmentPane.getView("assistant_marks_pane.fxml");
                assistantMarksPane.setName("Student Marks");
                assistantMarksPane.load((Teacher) user);
                resultsMenu.items.add(assistantMarksPane);

                CourseSummeryPane summeryPane = (CourseSummeryPane) FragmentPane.getView("course_summery_pane.fxml");
                summeryPane.setName("Course Summery");
                summeryPane.load((Teacher) user);
                resultsMenu.items.add(summeryPane);

                AssistantEducationalStatus educationalStatus = (AssistantEducationalStatus) FragmentPane.getView("assistant_educational_status.fxml");
                educationalStatus.setName("Educational Status");
                educationalStatus.load((Teacher) user);
                resultsMenu.items.add(educationalStatus);
            }


            resultsTab.setContent(resultsMenu.getView());
            tabPane.getTabs().add(resultsTab);
            resultsTab.setOnSelectionChanged(event -> resultsMenu.listView.getSelectionModel().clearSelection());
            // ********************* Results Tab End ****************

            // ******************** Account Info Tab *********************
            Tab accountInfoTab = new Tab("Account Info");
            TeacherAccountInfoPane infoPane = (TeacherAccountInfoPane) FragmentPane.getView("teacher_account_info_pane.fxml");
            infoPane.load((Teacher) user);
            accountInfoTab.setContent(infoPane.root);
            tabPane.getTabs().add(accountInfoTab);
            accountInfoTab.setOnSelectionChanged(event -> infoPane.load((Teacher) user));
            // *********************** Account Info Tab End **********************

            // ******************** New Student Tab **************************
            if (((Teacher) user).isAssistant()) {
                Tab newStudentTab = new Tab("New Student");
                NewStudentPane studentPane = (NewStudentPane) FragmentPane.getView("new_student_pane.fxml");
                studentPane.load((Teacher) user);
                newStudentTab.setContent(studentPane.root);
                tabPane.getTabs().add(newStudentTab);
                newStudentTab.setOnSelectionChanged(event -> studentPane.load((Teacher) user));

                Tab newTeacherTab = new Tab("New Teacher");
                NewTeacherPane teacherPane = (NewTeacherPane) FragmentPane.getView("new_teacher_pane.fxml");
                teacherPane.load((Teacher) user);
                newTeacherTab.setContent(teacherPane.root);
                tabPane.getTabs().add(newTeacherTab);
                newTeacherTab.setOnSelectionChanged(event -> teacherPane.load((Teacher) user));
            }
            // *********************** New Student Tab End *************************
        }

        Tab themeTab = new Tab("Theme");
        ChangeThemePane themePane = (ChangeThemePane) FragmentPane.getView("change_theme_pane.fxml");
        themePane.load(user);
        themeTab.setContent(themePane.root);
        tabPane.getTabs().add(themeTab);
    }

    public void logOut() throws IOException {
        context.logOut();
    }

}
