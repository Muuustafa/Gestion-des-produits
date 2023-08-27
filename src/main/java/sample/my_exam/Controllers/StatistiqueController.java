package sample.my_exam.Controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import sample.my_exam.Models.Category;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class StatistiqueController {
    @FXML
    private PieChart diagramme;


    public void initialize() {
        // Connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/examenjavafx";
        String user = "root";
        String password = "";
        try (Connection connection = (Connection) DriverManager.getConnection(url, user, password)) {
            // Requête SQL pour récupérer les catégories et le nombre de produits par catégorie
            String sql = "SELECT COUNT(idprod) AS NbrProd, categorie FROM produit GROUP BY categorie";
            try (PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Création de la liste de catégories
                    List<Category> list = new ArrayList<>();
                    while (resultSet.next()) {
                        int nbrProd = resultSet.getInt("NbrProd");
                        String libelle = resultSet.getString("categorie");
                        Category cat = new Category(libelle, nbrProd);
                        list.add(cat);
                    }

                    // Création de la liste de données pour le diagramme circulaire
                    List<Data> data = new ArrayList<>();
                    for (Category cat : list) {
                        data.add(new Data(cat.getName(), cat.getCount()));
                    }

                    // Configuration du diagramme circulaire
                    diagramme.setData(observableArrayList(data));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
