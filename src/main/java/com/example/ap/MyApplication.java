package com.example.ap;

import com.google.gson.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.University;
import logic.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;


public class MyApplication extends Application {
    static Logger logger = LogManager.getLogger(MyApplication.class);
    Stage stage;

    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> {
            if (localDateTime == null) return JsonNull.INSTANCE;
            return new JsonPrimitive(localDateTime.toString());
        });

        builder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement == JsonNull.INSTANCE) return null;
            return LocalDateTime.parse(jsonElement.getAsString());
        });

        builder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> {
            if (localDate == null) return JsonNull.INSTANCE;
            return new JsonPrimitive(localDate.toString());
        });

        builder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement == JsonNull.INSTANCE) return null;
            return LocalDate.parse(jsonElement.getAsString());
        });

        builder.registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (localTime, type, jsonSerializationContext) -> {
            if (localTime == null) return JsonNull.INSTANCE;
            return new JsonPrimitive(localTime.toString());
        });

        builder.registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement == JsonNull.INSTANCE) return null;
            return LocalTime.parse(jsonElement.getAsString());
        });

        builder.setPrettyPrinting();

        Gson gson = builder.create();

        //  This part of code writes data in JSON.txt file.
        /*University universityT = new University();
        universityT.init();
        String tJson = gson.toJson(universityT, University.class);
        try {
            FileWriter writer = new FileWriter("JSON.txt", false);
            writer.write(tJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        User.allUsers.clear();
        Department.allDepartments.clear();
        Course.allCourse.clear();
        Student.allStudents.clear();
        Teacher.allTeachers.clear();
        */

        StringBuilder json = new StringBuilder();
        try {
            File file = new File("JSON.txt");
            logger.info("Json File Opened");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info("JSON.txt wasn't found!");
        }


        University university = gson.fromJson(json.toString(), University.class);

        university.load();

        logger.info("Information Imported Successfully");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        logInView();
    }

    public void logInView() throws IOException {
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("log_in_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInViewController controller = fxmlLoader.getController();
        controller.init(this);
        stage.setScene(scene);
        stage.show();
    }

    public void mainPage(User user) throws IOException {
        stage.close();
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("main_page_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainPageViewController controller = fxmlLoader.getController();
        controller.init(this, user);
        user.lastLogIn = LocalDateTime.now();
        stage.setScene(scene);
        stage.show();
    }

    public void logOut() throws IOException {
        stage.close();
        logInView();
    }
}
