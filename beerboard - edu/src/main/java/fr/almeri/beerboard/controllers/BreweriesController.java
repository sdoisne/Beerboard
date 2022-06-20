package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class BreweriesController {

    @Autowired
    private BrasserieRepository brasserieRepository;

    @GetMapping("/breweries")
    public String getListeBrasseries(Model pModel) {
        ArrayList<Brasserie> listBrasserieFromDatabase = (ArrayList<Brasserie>) brasserieRepository.findAll();
        pModel.addAttribute("listBrasserieFromDB", listBrasserieFromDatabase);
        return "breweries";
    }

    @GetMapping("/add-brewery")
    public String getNouvelleBrasserie(Model pModel) {
//        biereRepository.save(Biere);
//        pModel.addAttribute("NouvelleBrasserie", nouvelleBrasserie);
        return "add-brewery";
    }
}
