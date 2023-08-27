package sample.my_exam.Controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.my_exam.Dao.BD;

import java.io.FileOutputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ExtraireController implements Initializable {
    BD connection;
    PreparedStatement st = null;
    PreparedStatement st1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    Connection con = null;

    @FXML
    private VBox vbox;

    @FXML
    void ExtraireExcel(ActionEvent event) {

        try {
            // Connexion à la base de données MySQL
            con = (Connection) connection.getConnection();

            // Requête pour extraire la liste de produits par catégorie
            String sql1 = "SELECT libelle FROM categorie ";
             st = (PreparedStatement) con.prepareStatement(sql1);
             rs = st.executeQuery();

            // Création du classeur Excel
            Workbook workbook = new XSSFWorkbook();

            int rownum = 0;
            // Pour chaque catégorie, extraire le nombre de produits correspondants
            while (rs.next()) {
                String categorie = rs.getString("libelle");
                String sql2 = "SELECT  COUNT(idprod) as NbrProd, categorie " +
                        "FROM produit " +
                        "WHERE categorie = '" + categorie + "' " +
                        "GROUP BY categorie";
                    st1 = (PreparedStatement) con.prepareStatement(sql2);
                    rs1 = st1.executeQuery();

                // Création de la feuille Excel pour la catégorie
                Sheet sheet = workbook.createSheet(categorie);


                // Ajout des en-têtes de colonne
                Row headerRow = sheet.createRow(rownum++);
                headerRow.createCell(0).setCellValue(rs.getString("libelle"));

                // Ajout de chaque produit à la feuille Excel
                while (rs1.next()) {
                    Row row = sheet.createRow(rownum++);
                    row.createCell(0).setCellValue(rs1.getInt("NbrProd"));
                }
            }

            // Écriture du classeur Excel dans un fichier
            FileOutputStream outputStream = new FileOutputStream("Liste des produits par categorie.xlsx");
            workbook.write(outputStream);
            workbook.close();
            con.close();

            //Message Alerte
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Export Excel réussi... ");
            alert.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    @FXML
    void ExtrairePDF(ActionEvent event) {
        // Connexion à la base de données MySQL
        con = (Connection) connection.getConnection();
        String query = "SELECT * FROM produit";
        try {
            // Requête pour extraire la liste de produits
            st = (PreparedStatement) con.prepareStatement(query);
            rs = st.executeQuery();
            // Création du document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Liste des produits.pdf"));
            document.open();

            // Ajout de chaque produit au document PDF
            while (rs.next()) {
                Paragraph p = new Paragraph(
                                rs.getString("libelle") + " \t     ** " +
                                "  " + rs.getString("quantite") + " \t     **" +
                                "  " + rs.getString("pu") + " FCFA" + " \t     ** " +
                                "  " + rs.getString("categorie") + "\n \n");
                document.add(p);
            }

            // Fermeture du document PDF et de la connexion à la base de données
            document.close();
            con.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Export de PDF reussi...");
            alert.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = new BD();
        con = (Connection) connection.getConnection();

    }
}


