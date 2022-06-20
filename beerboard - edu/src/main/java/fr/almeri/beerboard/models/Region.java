package fr.almeri.beerboard.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @Column(name = "nom_region")

    private String nomRegion;

    @Column(name = "nom_pays")
    private String nomPays;

    public Region () {
    }

    public String getNomRegion() {
        return nomRegion;
    }

    public void setNomRegion(String pNomRegion) {
        this.nomRegion = pNomRegion;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String pNomPays) {
        this.nomPays = pNomPays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(nomRegion, region.nomRegion) && Objects.equals(nomPays, region.nomPays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomRegion, nomPays);
    }

    @Override
    public String toString() {
        return "Region{" +
                "nomRegion='" + nomRegion + '\'' +
                ", nomPays='" + nomPays + '\'' +
                '}';
    }
}
