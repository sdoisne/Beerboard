package fr.almeri.beerboard.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="utilisateur")
public class Utilisateur implements Serializable {

    @Id
    private Integer idUtilisateur;

    @Column(name="prenom_utilisateur")
    private String prenomUtilisateur;

    @Column(name="nom_utilisateur")
    private String nomUtilisateur;

    @Column(name="email_utilisateur")
    private String emailUtilisateur;

    @Column(name="mdp_utilisateur")
    private String mdpUtilisateur;

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String pPrenomUtilisateur) {
        this.prenomUtilisateur = pPrenomUtilisateur;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String pEmailUtilisateur) {
        this.emailUtilisateur = pEmailUtilisateur;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer pIdUtilisateur) {
        this.idUtilisateur = pIdUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String pNomUtilisateur) {
        this.nomUtilisateur = pNomUtilisateur;
    }

    public String getMdpUtilisateur() {
        return mdpUtilisateur;
    }

    public void setMdpUtilisateur(String pMdpUtilisateur) {
        this.mdpUtilisateur = pMdpUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(idUtilisateur, that.idUtilisateur) && Objects.equals(prenomUtilisateur, that.prenomUtilisateur) && Objects.equals(nomUtilisateur, that.nomUtilisateur) && Objects.equals(emailUtilisateur, that.emailUtilisateur) && Objects.equals(mdpUtilisateur, that.mdpUtilisateur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtilisateur, prenomUtilisateur, nomUtilisateur, emailUtilisateur, mdpUtilisateur);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", prenomUtilisateur='" + prenomUtilisateur + '\'' +
                ", nomUtilisateur='" + nomUtilisateur + '\'' +
                ", emailUtilisateur='" + emailUtilisateur + '\'' +
                ", mdpUtilisateur='" + mdpUtilisateur + '\'' +
                '}';
    }
}
