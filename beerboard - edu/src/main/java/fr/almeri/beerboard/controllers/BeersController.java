package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.Biere;
import fr.almeri.beerboard.repositories.BiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class BeersController {

    @Autowired
    private BiereRepository biereRepository;

    @GetMapping("/beers")
    public String getListeBieres(Model pModel) {
        ArrayList<Biere> listBieresFromDatabase = (ArrayList<Biere>) biereRepository.findAll();
        pModel.addAttribute("listBieresFromDB", listBieresFromDatabase);
        return "beers";
    }

    @GetMapping("/add-beer")
    public String getNouvelleBiere(Model pModel) {
//        biereRepository.save(Biere);
//        pModel.addAttribute("NouvelleBiere", nouvelleBiere);
        return "add-beer";
    }

    @GetMapping("/see-beer")
    public String getFicheBiere(Model pModel) {
//        String [] ficheBiere = biereRepository.findById();
//        pModel.addAttribute("ficheBiere", ficheBiere);
        return "see-beer";
    }


}
