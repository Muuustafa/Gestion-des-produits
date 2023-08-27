package sample.my_exam.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.my_exam.Main;

import java.io.IOException;

public class HomeController {
    @FXML
    private AnchorPane img;

    @FXML
    private Menu categorie;

    @FXML
    private Menu extaire;

    @FXML
    private Menu produit;

    @FXML
    private Menu statistique;

    @FXML
    void GotoCategorie(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Categorie.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 540);
        Stage stage = new Stage();
        stage.setTitle("Categorie");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void GotoExtraire(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Extraire.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Extraction");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void GotoProduits(ActionEvent event)  throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Produit.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 652, 590);
        Stage stage = new Stage();
        stage.setTitle("Produits");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void GotoStatistique(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Statistique.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 540);
        Stage stage = new Stage();
        stage.setTitle("Statistique");
        stage.setScene(scene);
        stage.show();

    }

}