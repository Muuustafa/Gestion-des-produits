package sample.my_exam.Controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.my_exam.Dao.BD;
import sample.my_exam.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    BD connection;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;

    @FXML
    private Button BtnLogin;

    @FXML
    private Button BtnRegister;

    @FXML
    private TextField Text_Login;
    @FXML
    private Label LbErrors;
    @FXML
    private PasswordField Text_Password;

    @FXML
    private Label statusLabel;

    private String logIn() {
        String status = "Success";
        String login =  Text_Login.getText();
        String password = Text_Password.getText();
        if(login.isEmpty() || password.isEmpty()) {
            //setLblError(Color.TOMATO, "Veillez remplir les champs svp...");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Veillez remplir les champs svp...");
            alert.showAndWait();
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM user Where login = ? and password = ?";
            try {
                st = (PreparedStatement) con.prepareStatement(sql);
                st.setString(1, Text_Login.getText());
                st.setString(2, Text_Password.getText());
                rs = st.executeQuery();
                if (!rs.next()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setContentText("Login ou Mot de passe incorrect...");
                    alert.showAndWait();
                    //setLblError(Color.TOMATO, "Login ou Mot de passe incorrect...");
                    status = "Error";
                } else {
                    //setLblError(Color.GREEN, "Connexion avec succees... Redirecting...");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Connexion avec succees... Redirecting...");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    @FXML
    void GotoRegister(ActionEvent event) throws IOException {
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Register.fxml"));
        Stage fenetre = (Stage) BtnRegister.getScene().getWindow();
        fenetre.setScene(new Scene(root, 500,500));*/

        if (event.getSource() == BtnRegister)
        {
            /*try{
                FXMLLoader fxmlLoader = new  FXMLLoader(getClass().getResource("sample/projet/Fxml/Register.fxml"));
                Parent my_root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Register");
                stage.setScene(new Scene(my_root, 644,500));
                stage.show();
            }catch (Exception e)
            {
                System.out.println("Erreur de redirection de fenetre...");
            }*/
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 540);
            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new BD();
        con = (Connection) connection.getConnection();
    }


    public void Connecter(MouseEvent event) throws IOException {
        if (event.getSource() == BtnLogin) {
            //login here
            if (logIn().equals("Success")) {
                /*try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();

                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }*/
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Home.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 485, 355);
                Stage stage = new Stage();
                stage.setTitle("Accueil");
                stage.setScene(scene);
                stage.show();

            }
        }
    }
}