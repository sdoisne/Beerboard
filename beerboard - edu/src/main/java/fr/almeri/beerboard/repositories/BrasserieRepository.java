package fr.almeri.beerboard.repositories;

import fr.almeri.beerboard.models.Brasserie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BrasserieRepository extends CrudRepository<Brasserie, String> {

    @Query("SELECT DISTINCT b.region.nomRegion FROM Brasserie b GROUP BY b.region.nomRegion ORDER BY b.region.nomRegion")
    public ArrayList<String> getListeRegions();

    @Query("SELECT COUNT(b.codeBrasserie) FROM Brasserie b GROUP BY b.region.nomRegion ORDER BY b.region.nomRegion")
    public Integer [] getRepartitionBrasserieParRegion();

}
