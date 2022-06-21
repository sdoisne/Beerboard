package fr.almeri.beerboard.repositories;


import fr.almeri.beerboard.models.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

// @Repository indique que c'est dans cette interface qu'on effectue les requêtes
// extends CrudRepository<NomDeLaClasse, TypeID>
@Repository
public interface RegionRepository extends CrudRepository<Region, String> {

    // COMMIT DU 21/06/2022 à 14H
}
