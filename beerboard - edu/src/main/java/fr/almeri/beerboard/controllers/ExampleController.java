package fr.almeri.beerboard.controllers;

// pour l'instant, cet exemple de controller ne communique pas avec la base de données

import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.models.Marque;
import fr.almeri.beerboard.models.Pays;
import fr.almeri.beerboard.models.Type;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.MarqueRepository;
import fr.almeri.beerboard.repositories.PaysRepository;
import fr.almeri.beerboard.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

// Permet d'indiquer à SpringBoot qu'il y aura un "routage" dans ce fichier
@Controller
public class ExampleController {


    // @Autowired permet d'instancier automatiqueemnt PaysRepository si besoin
    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private MarqueRepository marqueRepository;


    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private BrasserieRepository brasserieRepository;

    // Permet de définir une route appelée avec la méthode GET ("directement via l'URL")
    // Ici, localhost:8888/example
    @GetMapping("/example")
        public String getPageExample(Model pModel) {
        // l'objet Model est instancié lorsque l'on appelle la méthode getPageExample par SpringBoot
        // Il permet d'envoyer des données dynamiques à la page
        // La méthode addAttribute prend deux paramètres : le 1er est le nom de la variable à utiliser dans l'HTML
        // Et le 2ème est la donnée à afficher
        pModel.addAttribute("prenom","Sylvain");

//        Pays pays = new Pays();
//        pays.setNomPays("France");
//        pays.setConsommation(145.0);
//        pays.setProduction(190.0);
//
//        Pays pays2 = new Pays();
//        pays2.setNomPays("Angleterre");
//        pays2.setConsommation(15.0);
//        pays2.setProduction(390.0);
//
//        Pays pays3 = new Pays();
//        pays3.setNomPays("Allemagne");
//        pays3.setConsommation(255.0);
//        pays3.setProduction(160.0);
//
//        // Création d'une liste de pays
//        ArrayList<Pays> listPays = new ArrayList<>();
//        listPays.add(pays);
//        listPays.add(pays2);
//        listPays.add(pays3);
//
//        pModel.addAttribute("pays",pays);
//        pModel.addAttribute("listPays", listPays);


        // Création de la liste contenant tous les pays et leurs attributs directement de la BDD
        ArrayList<Pays> listPaysFromDatabase = (ArrayList<Pays>) paysRepository.findAll();
        pModel.addAttribute("listPaysFromDB", listPaysFromDatabase);

        ArrayList<Brasserie> listBrasserieFromDatabase = (ArrayList<Brasserie>) brasserieRepository.findAll();
        pModel.addAttribute("listBrasserieFromDB", listBrasserieFromDatabase);

        ArrayList<Type> listTypeFromDatabase = (ArrayList<Type>) typeRepository.findAll();
        pModel.addAttribute("listTypeFromDB", listTypeFromDatabase);

        ArrayList<Marque> listMarqueFromDatabase = (ArrayList<Marque>) marqueRepository.findAll();
        pModel.addAttribute("listMarqueFromDB", listMarqueFromDatabase);

        // Méthode permettant d'indiquer quelle page HTML est renvoyée
        // Ici, example.html se trouvant de le répertoire /templates
            return "example";
    }
    // COMMIT DU 21/06/2022 à 14H
}
