package fr.almeri.beerboard.repositories;

import fr.almeri.beerboard.models.Utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UtilisateurRepository extends CrudRepository <Utilisateur, Integer> {

    @Query("SELECT u.emailUtilisateur FROM Utilisateur u WHERE u.emailUtilisateur = :email")
    public String emailExistant(String email);

    @Query("SELECT u FROM Utilisateur u WHERE u.emailUtilisateur = :email")
    public Utilisateur compteExistant(String email);


}
