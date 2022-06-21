package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.Biere;
import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.models.Region;
import fr.almeri.beerboard.repositories.BiereRepository;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class BreweriesController {

    @Autowired
    private BrasserieRepository brasserieRepository;

    @Autowired
    private BiereRepository biereRepository;

    @Autowired
    private RegionRepository regionRepository;

    @GetMapping("/breweries")
    public String getListeBrasseries(Model pModel) {
        ArrayList<Brasserie> listBrasserieFromDatabase = (ArrayList<Brasserie>) brasserieRepository.findAll();
        pModel.addAttribute("listBrasserieFromDB", listBrasserieFromDatabase);
        return "breweries";
    }

    @GetMapping("/see-brewery/{code}")
    public String getNouvelleBrasserie(Model pModel, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);

        return "see-brewery";
    }

    @GetMapping("/add-brewery")
    public String getNouvelleBrasserie(Model pModel){
        ArrayList<Region> ListRegion = (ArrayList<Region>) regionRepository.findAll();
        pModel.addAttribute("listRegion", ListRegion);
        return "add-brewery";
    }
}
