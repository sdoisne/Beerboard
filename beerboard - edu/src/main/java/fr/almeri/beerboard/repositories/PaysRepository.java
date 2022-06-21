package fr.almeri.beerboard.repositories;


import fr.almeri.beerboard.models.Pays;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// @Repository indique que c'est dans cette interface qu'on effectue les requêtes
// extends CrudRepository<NomDeLaClasse, TypeID>
@Repository
public interface PaysRepository extends CrudRepository<Pays, String> {

    // JPQL : java persisten query language (SQL adapté au langage JAVA)
    // SELECT nom_pays FROM pays ORDER BY nom_pays ASC;
    // SELECT classique mais obligation de définir un alias pour la table utilisée
    @Query("SELECT p.nomPays FROM Pays p ORDER BY p.nomPays ASC")
    // Etant dans une interface, on n'implémente pas la méthode.
    // L'annotation se charge de faire le lien
    public ArrayList<String> getListNomPays();

    @Query("SELECT p.consommation FROM Pays p ORDER BY p.nomPays ASC")
    public ArrayList<Integer> getConsommationPays();

    @Query("SELECT p.production FROM Pays p ORDER BY p.nomPays ASC")
    public ArrayList<Integer> getProductionPays();

}
