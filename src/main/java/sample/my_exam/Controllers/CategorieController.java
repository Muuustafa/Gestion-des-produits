package sample.my_exam.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.my_exam.Dao.BD;
import sample.my_exam.Models.Categorie;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategorieController implements Initializable {
    BD connection;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Categorie> tableCategorie;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Categorie, Integer> col_id = new TableColumn<Categorie, Integer>();;

    @FXML
    private TableColumn<Categorie, String> col_libelle = new TableColumn<Categorie, String>();;

    @FXML
    private TextField text_id;
    @FXML
    private TextField text_libelle;

    @FXML
    void ajouter(ActionEvent event) {
        this.con = this.connection.getConnection();

        if (text_libelle.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Veillez renseigner le champ svp...");
            alert.showAndWait();
        }else {
        String insert = "INSERT INTO categorie (libelle) VALUES (?)";

        try {
            this.st = this.con.prepareStatement(insert);
            this.st.setString(1, this.text_libelle.getText());
            this.st.executeUpdate();
            this.affiche();
        } catch (SQLException var4) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, (String)null, var4);
         }
      }
    }


    @FXML
    void effacer(ActionEvent event) {
        this.text_libelle.setText((String)null);
    }

    @FXML
    void modifier(ActionEvent event) {
        String update = "UPDATE categorie SET libelle = ? where idcat = ?";
        try {
            st = con.prepareStatement(update);
            st.setString(1, text_libelle.getText());
            st.setInt(2, Integer.parseInt(text_id.getText()));
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(CategorieController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void supprimer(ActionEvent event) {
        int index=this.tableCategorie.getSelectionModel().getSelectedIndex();

        int id = this.tableCategorie.getItems().get(index).getId();
        String delete = "DELETE FROM categorie  where idcat = ?";
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(CategorieController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }
    public ObservableList<Categorie> getCategorie() {
        ObservableList<Categorie> list = FXCollections.observableArrayList();
        String select = "SELECT * from categorie";

        try {
            st = con.prepareStatement(select);
            rs = st.executeQuery();

            while(rs.next()) {
                Categorie cat = new Categorie();
                cat.setId(rs.getInt("idcat"));
                cat.setLibelle(rs.getString("libelle"));
                list.add(cat);
            }
        } catch (SQLException var4) {
            Logger.getLogger(CategorieController.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

        return list;
    }
    public void affiche() {
        ObservableList<Categorie> list = getCategorie();
        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_libelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        tableCategorie.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new BD();
        con = connection.getConnection();
        affiche();
        text_id.setVisible(false);
        col_id.setCellValueFactory(new PropertyValueFactory("id"));

    }

    @FXML
    void charge(javafx.scene.input.MouseEvent event) {

        int index=this.tableCategorie.getSelectionModel().getSelectedIndex();
        Categorie cat = this.tableCategorie.getItems().get(index);
        text_id.setText(String.valueOf(cat.getId()));
        text_libelle.setText(cat.getLibelle());

    }
}
