package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.*;
import fr.almeri.beerboard.repositories.BiereRepository;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getFicheBrasserieConsultation(Model pModel, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);

        return "see-brewery";
    }

    @GetMapping("/modify-brewery/{code}")
    public String getFicheBrasserieModification(Model pModel, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);

        return "modify-brewery";
    }

    @GetMapping("/delete-brewery/{code}")
    public String getFicheBrasserieSuppression(Model pModel, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);

        return "delete-brewery";
    }

    @GetMapping("/add-brewery")
    public String getNouvelleBrasserie(Model pModel){
        ArrayList<Region> ListRegion = (ArrayList<Region>) regionRepository.findAll();
        pModel.addAttribute("listRegion", ListRegion);
        return "add-brewery";
    }

    @PostMapping("/valid-brewery")
    public String addNouvelleBrasserie(@ModelAttribute Brasserie brasserie, RedirectAttributes redir){
        if (!brasserieRepository.existsById(brasserie.getCodeBrasserie())){
            brasserieRepository.save(brasserie);
            return "redirect:/breweries";
        } else{
            redir.addFlashAttribute("msg", "L'identifiant de la brasserie existe déjà, veuillez en saisir un nouveau ou vérifier que cette brasserie n'existe pas déjà.");
            return "redirect:/add-brewery";
        }
    }

    @PostMapping("/update-brewery")
    public String updateBrasserie(@ModelAttribute Brasserie brasserie){
        brasserieRepository.save(brasserie);
        return "redirect:/breweries";
    }

    @PostMapping("/drop-brewery")
    public String deleteBrasserie(@ModelAttribute Brasserie brasserie){
        brasserieRepository.deleteById(brasserie.getCodeBrasserie());
        return "redirect:/breweries";
    }
}
