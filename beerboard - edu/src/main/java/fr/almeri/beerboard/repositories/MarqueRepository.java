package fr.almeri.beerboard.repositories;

import fr.almeri.beerboard.models.Marque;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MarqueRepository extends CrudRepository<Marque, String> {

    @Query("SELECT DISTINCT m.brasserie.nomBrasserie FROM Marque m")
    public String [] getListeBrasserie();

    @Query("SELECT COUNT(m.nomMarque) FROM Marque m GROUP BY m.brasserie.codeBrasserie ORDER BY m.brasserie.nomBrasserie")
    public Integer [] getNombreMarquesParBrasserie();

    // COMMIT DU 21/06/2022 à 14H
}
