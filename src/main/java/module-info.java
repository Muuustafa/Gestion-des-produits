module sample.my_exam {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.net.http;
    requires itextpdf;
    requires kernel;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens sample.my_exam to javafx.fxml;
    exports sample.my_exam;
    exports sample.my_exam.Controllers;
    opens sample.my_exam.Controllers to javafx.fxml;
    opens sample.my_exam.Models;

}