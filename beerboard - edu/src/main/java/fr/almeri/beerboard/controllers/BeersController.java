package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.*;
import fr.almeri.beerboard.repositories.BiereRepository;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.MarqueRepository;
import fr.almeri.beerboard.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class BeersController {

    @Autowired
    private BiereRepository biereRepository;

    @Autowired
    private MarqueRepository marqueRepository;

    @Autowired
    private TypeRepository typeRepository;


    @GetMapping("/beers")
    public String getListeBieres(Model pModel) {
        ArrayList<Biere> listBieresFromDatabase = (ArrayList<Biere>) biereRepository.findAll();
        pModel.addAttribute("listBieresFromDB", listBieresFromDatabase);
        return "beers";
    }


    @GetMapping("/see-beer")
    public String getFicheBiere(Model pModel, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque),version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        return "see-beer";
    }

    @GetMapping("/add-beer")
    public String getNouvelleBrasserie(Model pModel){
        ArrayList<Marque> listMarque = (ArrayList<Marque>) marqueRepository.findAll();
        pModel.addAttribute("listMarque", listMarque);
        ArrayList<Type> listType = (ArrayList<Type>) typeRepository.findAll();
        pModel.addAttribute("listType", listType);
        return "add-beer";
    }

    // COMMIT DU 21/06/2022 à 14H
}
