package sample.my_exam.Dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class BD {

    private final String server = "Localhost";
    private  final String username = "root";
    private final String password = "";
    private final String bd = "examenjavafx";
    private final String url = ""
            + "jdbc:mysql://"+server+":3306/"+bd;
    private Connection conn;

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.print("Connecté");
        } catch (Exception ex) {
            conn = null;
            System.out.print("Erreur");
        }

        return conn;
    }
    //public ObservablebleList<Classe> getClasseList()
    {
        //ObservableList<Classe> classeList = FXCollections
    }
}