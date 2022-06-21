package fr.almeri.beerboard.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class BiereId implements Serializable {

    public BiereId() {

    }

    public BiereId(Marque marque, String version) {
        // A partir d'une chaine de caractère nomMarque, on créé un objet Marque
        this.marque = marque;
        this.version = version;
    }
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nom_marque") // Permet de définir la colonne qui possède une clef étrangère
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Marque marque;

    @Id
    @Column(name = "version")
    private String version;

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiereId biereId = (BiereId) o;
        return Objects.equals(marque, biereId.marque) && Objects.equals(version, biereId.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marque, version);
    }
}
