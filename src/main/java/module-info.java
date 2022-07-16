module com.example.ap {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.log4j;


    opens com.example.ap to javafx.fxml;
    exports com.example.ap;
    exports logic;
    opens logic to javafx.fxml;
}