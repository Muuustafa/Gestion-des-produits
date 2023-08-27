package sample.my_exam.Models;

public class Produit {
    private int id;
    private String libelle;
    private String quantite;
    private String pu;
    private String idCat;

    public Produit() {
    }

    public Produit(int id, String libelle, String quantite, String pu, String idCat) {
        this.id = id;
        this.libelle = libelle;
        this.quantite = quantite;
        this.pu = pu;
        this.idCat = idCat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getPu() {
        return pu;
    }

    public void setPu(String pu) {
        this.pu = pu;
    }

    public String getIdCat() {
        return idCat;
    }

    public void setIdCat(String idCat) {
        this.idCat = idCat;
    }

}