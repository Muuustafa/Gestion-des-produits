package sample.my_exam.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sample.my_exam.Dao.BD;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

//import static com.sun.javafx.application.PlatformImpl.exit;
//import static jdk.internal.net.http.common.Utils.close;

public class RegisterController implements Initializable {
    BD connection;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;

    @FXML
    private Button Cancel;

    @FXML
    private Button Confirm;

    @FXML
    private TextField Txt_Login;

    @FXML
    private TextField Txt_Nom;

    @FXML
    private TextField Txt_Password;
    @FXML
    private Label LabelConfirm;
    @FXML
    private TextField Txt_Prenom;

    @FXML
    private TextField Txt_Telephone;

    @FXML
    private TextField Txt_email;

    @FXML
    private Label statusLabel;

    @FXML
    void Confirmation(ActionEvent event) {
        con = connection.getConnection();

        if(Txt_Nom.getText().isEmpty() || Txt_Prenom.getText().isEmpty() || Txt_Telephone.getText().isEmpty() || Txt_email.getText().isEmpty() || Txt_Login.getText().isEmpty() || Txt_Password.getText().isEmpty()) {
            //setLblError(Color.TOMATO, "Veillez remplir les champs svp...");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Veillez remplir les champs svp...");
            alert.showAndWait();
        } else {
            String insert = "INSERT INTO user (nom,prenom,telephone,email,login,password) VALUES (?,?,?,?,?,?)";
            try {
                    st = (com.mysql.jdbc.PreparedStatement) con.prepareStatement(insert);
                    st.setString(1, Txt_Nom.getText());
                    st.setString(2, Txt_Prenom.getText());
                    st.setString(3, Txt_Telephone.getText());
                    st.setString(4, Txt_email.getText());
                    st.setString(5, Txt_Login.getText());
                    st.setString(6, Txt_Password.getText());
                    st.executeUpdate();
                    //setLblError(Color.GREEN, "Utilisateur ajouté avec succees...");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Utilisateur ajouté avec succees...");
                    alert.showAndWait();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }
    private void setLblError(Color color, String text) {
        LabelConfirm.setTextFill(color);
        LabelConfirm.setText(text);
        System.out.println(text);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new BD();
        con = connection.getConnection();

    }
}