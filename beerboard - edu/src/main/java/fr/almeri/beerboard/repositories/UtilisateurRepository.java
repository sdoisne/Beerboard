package fr.almeri.beerboard.repositories;

import fr.almeri.beerboard.models.Utilisateur;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurRepository extends CrudRepository <Utilisateur, Integer> {

}
