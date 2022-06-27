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

import javax.servlet.http.HttpSession;
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
    public String getListeBieres(Model pModel, HttpSession session) {
        ArrayList<Biere> listBieresFromDatabase = (ArrayList<Biere>) biereRepository.findAll();
        pModel.addAttribute("listBieresFromDB", listBieresFromDatabase);
        if (session.getAttribute("infoConnexion") != null) {
            return "beers";
        }
        return "redirect:/login";
    }


    @GetMapping("/see-beer")
    public String getFicheBiereConsultation(Model pModel, HttpSession session, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque), version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        if (session.getAttribute("infoConnexion") != null) {
            return "see-beer";
        }
        return "redirect:/login";
    }

    @GetMapping("/modify-beer")
    public String getFicheBiereModification(Model pModel, HttpSession session, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque), version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        if (session.getAttribute("infoConnexion") != null) {
            return "modify-beer";
        }
        return "redirect:/login";
    }

    @GetMapping("/delete-beer")
    public String getFicheBiereSuppression(Model pModel, HttpSession session, @RequestParam(required = true) String marque, @RequestParam(required = true) String version) {
        BiereId biereId = new BiereId(new Marque(marque), version);
        pModel.addAttribute("biere", biereRepository.findById(biereId).orElseThrow());
        if (session.getAttribute("infoConnexion") != null) {
            return "delete-beer";
        }
        return "redirect:/login";
    }

    @GetMapping("/add-beer")
    public String getNouvelleBrasserie(Model pModel, HttpSession session) {
        ArrayList<Marque> listMarque = (ArrayList<Marque>) marqueRepository.findAll();
        pModel.addAttribute("listMarque", listMarque);
        ArrayList<Type> listType = (ArrayList<Type>) typeRepository.findAll();
        pModel.addAttribute("listType", listType);
        if (session.getAttribute("infoConnexion") != null) {
            return "add-beer";
        }
        return "redirect:/login";
    }

    @PostMapping("/valid-beer")
    public String addNouvelleBiere(@ModelAttribute Biere biere, HttpSession session, RedirectAttributes redir) {
        BiereId id = new BiereId(biere.getMarque(), biere.getVersion());
        if (session.getAttribute("infoConnexion") != null) {
            if (!biereRepository.existsById(id)) {
                biereRepository.save(biere);
                return "redirect:/beers";
            } else {
                redir.addFlashAttribute("msg", "Une bière de cette marque avec cette version existe déjà, veuillez saisir une nouvelle version.");
                return "redirect:/add-beer";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/update-beer")
    public String updateBiere(@ModelAttribute Biere biere, HttpSession session) {
        BiereId id = new BiereId(biere.getMarque(), biere.getVersion());
        Biere oldBiere = biereRepository.findById(id).orElseThrow();
        biere.setType(oldBiere.getType());
        biereRepository.save(biere);
        if (session.getAttribute("infoConnexion") != null) {
            return "redirect:/beers";
        }
        return "redirect:/login";
    }

    @PostMapping("/drop-beer")
    public String deleteBiere(@ModelAttribute Biere biere, HttpSession session) {
        biereRepository.deleteById(new BiereId(biere.getMarque(), biere.getVersion()));
        if (session.getAttribute("infoConnexion") != null) {
            return "redirect:/beers";
        }
        return "redirect:/login";
    }
}
