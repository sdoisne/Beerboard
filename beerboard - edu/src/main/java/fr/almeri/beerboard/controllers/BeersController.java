package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.*;
import fr.almeri.beerboard.repositories.BiereRepository;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.MarqueRepository;
import fr.almeri.beerboard.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String getFicheBiereConsultation(Model pModel, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque),version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        return "see-beer";
    }

    @GetMapping("/modify-beer")
    public String getFicheBiereModification(Model pModel, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque),version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        return "modify-beer";
    }

    @GetMapping("/delete-beer")
    public String getFicheBiereSuppression(Model pModel, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque),version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        return "delete-beer";
    }

    @GetMapping("/add-beer")
    public String getNouvelleBrasserie(Model pModel){
        ArrayList<Marque> listMarque = (ArrayList<Marque>) marqueRepository.findAll();
        pModel.addAttribute("listMarque", listMarque);
        ArrayList<Type> listType = (ArrayList<Type>) typeRepository.findAll();
        pModel.addAttribute("listType", listType);
        return "add-beer";
    }

    @PostMapping("/valid-beer")
    public String addNouvelleBiere(@ModelAttribute Biere biere, RedirectAttributes redir){
        BiereId id = new BiereId(biere.getMarque(), biere.getVersion());
        if (!biereRepository.existsById(id)) {
            biereRepository.save(biere);
            return "redirect:/beers";
        } else {
            redir.addFlashAttribute("msg", "Une bière de cette marque avec cette version existe déjà, veuillez saisir une nouvelle version.");
            return "redirect:/add-beer";
        }
    }

    @PostMapping("/update-beer")
    public String updateBiere(@ModelAttribute Biere biere){
        BiereId id = new BiereId(biere.getMarque(), biere.getVersion());
        Biere oldBiere = biereRepository.findById(id).orElseThrow();
        biere.setType(oldBiere.getType());
        biereRepository.save(biere);
        return "redirect:/beers";
    }

    @PostMapping("/drop-beer")
    public String deleteBiere(@ModelAttribute Biere biere){
        biereRepository.deleteById(new BiereId(biere.getMarque(), biere.getVersion()));
        return "redirect:/beers";
    }
}
