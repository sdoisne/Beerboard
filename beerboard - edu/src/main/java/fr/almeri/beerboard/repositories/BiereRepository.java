package fr.almeri.beerboard.repositories;

import fr.almeri.beerboard.models.Biere;
import fr.almeri.beerboard.models.BiereId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BiereRepository extends CrudRepository<Biere, BiereId> {


    @Query("SELECT DISTINCT b.tauxAlcool FROM Biere b GROUP BY b.tauxAlcool ORDER BY b.tauxAlcool ASC")
    public Integer [] getTauxAlcool();

    @Query("SELECT COUNT(b.version) FROM Biere b GROUP BY b.tauxAlcool ORDER BY b.tauxAlcool ASC")
    public Integer [] getNombreBieres();

    @Query("SELECT distinct  b.marque.nomMarque FROM Biere b group by b.marque.nomMarque order by b.marque.nomMarque")
    public ArrayList<String> getListeMarques();

    @Query("SELECT COUNT(b.version) FROM Biere b GROUP BY b.marque.nomMarque ORDER BY b.marque.nomMarque ASC")
    public Integer [] getNombreVersionsParMarque();

    @Query("SELECT b FROM Biere b WHERE b.marque.brasserie.codeBrasserie = :code ORDER BY b.marque.nomMarque, b.version ASC")
    public ArrayList<Biere> getListeMarquesVersions(String code);

}
