package sample.my_exam.Controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import sample.my_exam.Dao.BD;
import sample.my_exam.Models.Produit;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProduitController  implements Initializable {
    BD connection;
    PreparedStatement st = null;
    ResultSet rs = null;
    Connection con = null;
    @FXML
    private ComboBox<String> Combo_Categorie;

    @FXML
    private TextField Text_Rechercher;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;
    @FXML
    private Label LbErrors;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> col_Categorie = new TableColumn<>();

    @FXML
    private TableColumn<?, ?> col_Pu = new TableColumn<>();

    @FXML
    private TableColumn<?, ?> col_Quantite = new TableColumn<>();

    @FXML
    private TableColumn<?, ?> col_id = new TableColumn<>();

    @FXML
    private TableColumn<?, ?> col_libelle =new TableColumn<>();

    @FXML
    private TableView<Produit> tableProduit;

    @FXML
    private TextField text_Pu;

    @FXML
    private TextField text_Quantite;

    @FXML
    private TextField text_id;

    @FXML
    private TextField text_libelle;

    private ObservableList<Produit> produitList = FXCollections.observableArrayList();


    @FXML
    void ajouter(ActionEvent event) {
        con = (Connection) connection.getConnection();
        if (text_libelle.getText().isEmpty() || text_Quantite.getText().isEmpty() || text_Pu.getText().isEmpty() || Combo_Categorie.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Veillez remplir les champs svp...");
            alert.showAndWait();
        } else if (Integer.parseInt(text_Quantite.getText()) < 5) {
            setLblError(Color.TOMATO, "Veillez saisir une quantité supérieure à 5 ...");
        }else {
            String insert = "INSERT INTO produit (libelle,quantite,pu,categorie) VALUES (?,?,?,?)";
            try {
                st = (PreparedStatement) con.prepareStatement(insert);
                st.setString(1, text_libelle.getText());
                st.setString(2, text_Quantite.getText());
                st.setString(3, text_Pu.getText());
                st.setString(4, Combo_Categorie.getSelectionModel().getSelectedItem());
                st.executeUpdate();
                affichage();
            } catch (SQLException ex) {
                Logger.getLogger(ProduitController.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void effacer(ActionEvent event) {
        text_libelle.setText(null);
        text_Quantite.setText(null);
        text_Pu.setText(null);
        Combo_Categorie.getSelectionModel().selectFirst();
    }

    @FXML
    void modifier(ActionEvent event) {
        String update = "UPDATE produit SET libelle =?,quantite = ?,pu =? , categorie = ? where idprod = ?";

        try {
            this.st = (PreparedStatement) this.con.prepareStatement(update);
            this.st.setString(1, this.text_libelle.getText());
            this.st.setString(2, this.text_Quantite.getText());
            this.st.setString(3, this.text_Pu.getText());
            this.st.setString(4, (String)this.Combo_Categorie.getSelectionModel().getSelectedItem());
            this.st.setInt(5, Integer.parseInt(this.text_id.getText()));
            this.st.executeUpdate();
            this.affichage();
        } catch (SQLException var4) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

    }

    @FXML
    void supprimer(ActionEvent event) {
        int index=this.tableProduit.getSelectionModel().getSelectedIndex();

        int id= this.tableProduit.getItems().get(index).getId();
        String delete = "DELETE FROM produit  where idprod = ?";
        try {
            st = (PreparedStatement) con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            affichage();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }
    public ObservableList<Produit> getProduit() {
        ObservableList<Produit> list = FXCollections.observableArrayList();
        String select = "SELECT * FROM produit";

        try {
            this.st = (PreparedStatement) this.con.prepareStatement(select);
            this.rs = this.st.executeQuery();

            while(this.rs.next()) {
                Produit pro = new Produit();
                pro.setId(this.rs.getInt("idprod"));
                pro.setLibelle(this.rs.getString("libelle"));
                pro.setQuantite(this.rs.getString("quantite"));
                pro.setPu(this.rs.getString("pu"));
                pro.setIdCat(this.rs.getString("categorie"));
                list.add(pro);
            }
        } catch (SQLException var4) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, (String)null, var4);
        }

        return list;
    }

    public void affichage() {
        ObservableList<Produit> list = getProduit();
        col_id.setCellValueFactory(new PropertyValueFactory("id"));
        col_libelle.setCellValueFactory(new PropertyValueFactory("libelle"));
        col_Quantite.setCellValueFactory(new PropertyValueFactory("quantite"));
        col_Pu.setCellValueFactory(new PropertyValueFactory("pu"));
        col_Categorie.setCellValueFactory(new PropertyValueFactory("idCat"));
        tableProduit.setItems(list);
    }
    @FXML
    void Remplir(javafx.scene.input.MouseEvent event) {

        int index = tableProduit.getSelectionModel().getSelectedIndex();
        Produit pro = (Produit) tableProduit.getItems().get(index);
        this.text_id.setText(String.valueOf(pro.getId()));
        this.text_libelle.setText(pro.getLibelle());
        this.text_Quantite.setText(pro.getQuantite());
        this.text_Pu.setText(pro.getPu());
        this.Combo_Categorie.setValue(pro.getIdCat());

    }
    @FXML
    public void RechercherProduit(ActionEvent event) {
        String searchText = Text_Rechercher.getText();
        ObservableList<Produit> searchResults = FXCollections.observableArrayList();

        // Requête pour récupérer les produits correspondant à la recherche
        String query = "SELECT * FROM produit WHERE libelle LIKE lower(?)";
        try {
            st = (PreparedStatement) con.prepareStatement(query);
            st.setString(1, "%" + searchText + "%");
            rs = st.executeQuery();
            while (rs.next()) {
                Produit pro = new Produit();
                pro.setId(this.rs.getInt("idprod"));
                pro.setLibelle(this.rs.getString("libelle"));
                pro.setQuantite(this.rs.getString("quantite"));
                pro.setPu(this.rs.getString("pu"));
                pro.setIdCat(this.rs.getString("categorie"));
                searchResults.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Affichage des résultats dans le TableView
        tableProduit.setItems(searchResults);
    }
    private void setLblError(Color color, String text) {
        LbErrors.setTextFill(color);
        LbErrors.setText(text);
        System.out.println(text);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new BD();
        con = (Connection) connection.getConnection();
        affichage();
        text_id.setVisible(false);

        String select = "SELECT * from categorie";

        ObservableList<String> ComboCategorie = FXCollections.observableArrayList();

        try {
            this.st = (PreparedStatement) this.con.prepareStatement(select);
            this.rs = this.st.executeQuery();

            while (this.rs.next()) {
                ComboCategorie.add(rs.getString("libelle"));
            }
        } catch (SQLException var4) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, (String) null, var4);
        }
        Combo_Categorie.setItems(ComboCategorie);

    }
}


