package fr.almeri.beerboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

// @Entity et @Table : association de la table à l'objet
// Il faut respecter le nom de la table dans la BDD !!
@Entity
@Table(name = "pays")
public class Pays {

    // @Id : Association de la clé primaire à l'attribut correspondant
    @Id
    // @Column : association de la colonne de la table à l'attribut de l'objet
    // Il faut respecter le nom de la colonne dans la BDD !!
    @Column(name = "nom_pays")
    private String nomPays;

    // @Column : association de la colonne de la table à l'attribut de l'objet
    // Il faut respecter le nom de la colonne dans la BDD !!
    @Column(name = "consommation")
    private Double consommation;

    // @Column : association de la colonne de la table à l'attribut de l'objet
    // Il faut respecter le nom de la colonne dans la BDD !!
    @Column(name = "production")
    private Double production;

    public Pays() {

    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String pNomPays) {
        this.nomPays = pNomPays;
    }

    public Double getConsommation() {
        return consommation;
    }

    public void setConsommation(Double pConsommation) {
        this.consommation = pConsommation;
    }

    public Double getProduction() {
        return production;
    }

    public void setProduction(Double pProduction) {
        this.production = pProduction;
    }

    @Override
    public String toString() {
        return "Pays{" +
                "nomPays='" + nomPays + '\'' +
                ", consommation=" + consommation +
                ", production=" + production +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pays pays = (Pays) o;
        return Objects.equals(nomPays, pays.nomPays) && Objects.equals(consommation, pays.consommation) && Objects.equals(production, pays.production);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomPays, consommation, production);
    }
}

