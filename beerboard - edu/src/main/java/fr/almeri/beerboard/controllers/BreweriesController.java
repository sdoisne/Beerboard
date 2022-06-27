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

import javax.servlet.http.HttpSession;
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
    public String getListeBrasseries(Model pModel, HttpSession session) {
        ArrayList<Brasserie> listBrasserieFromDatabase = (ArrayList<Brasserie>) brasserieRepository.findAll();
        pModel.addAttribute("listBrasserieFromDB", listBrasserieFromDatabase);
        if (session.getAttribute("infoConnexion") != null) {
            return "breweries";
        }
        return "redirect:/";
    }

    @GetMapping("/see-brewery/{code}")
    public String getFicheBrasserieConsultation(Model pModel, HttpSession session, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);
        if (session.getAttribute("infoConnexion") != null) {

            return "see-brewery";
        }
        return "redirect:/";
    }

    @GetMapping("/modify-brewery/{code}")
    public String getFicheBrasserieModification(Model pModel, HttpSession session, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);
        if (session.getAttribute("infoConnexion") != null) {

            return "modify-brewery";
        }
        return "redirect:/";
    }

    @GetMapping("/delete-brewery/{code}")
    public String getFicheBrasserieSuppression(Model pModel, HttpSession session, @PathVariable String code) {

        Brasserie brasserie = brasserieRepository.findById(code).orElseThrow();
        pModel.addAttribute("brasserie", brasserie);

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(code);
        pModel.addAttribute("bieres", bieres);

        if (session.getAttribute("infoConnexion") != null) {
            return "delete-brewery";
        }
        return "redirect:/";
    }

    @GetMapping("/add-brewery")
    public String getNouvelleBrasserie(Model pModel, HttpSession session) {
        ArrayList<Region> ListRegion = (ArrayList<Region>) regionRepository.findAll();
        pModel.addAttribute("listRegion", ListRegion);
        if (session.getAttribute("infoConnexion") != null) {
            return "add-brewery";
        }
        return "redirect:/";
    }

    @PostMapping("/valid-brewery")
    public String addNouvelleBrasserie(@ModelAttribute Brasserie brasserie, HttpSession session, RedirectAttributes redir) {
        if (session.getAttribute("infoConnexion") != null) {
            if (!brasserieRepository.existsById(brasserie.getCodeBrasserie())) {
                brasserieRepository.save(brasserie);
                return "redirect:/breweries";
            } else {
                redir.addFlashAttribute("msg", "L'identifiant de la brasserie existe déjà, veuillez en saisir un nouveau ou vérifier que cette brasserie n'existe pas déjà.");
                return "redirect:/add-brewery";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/update-brewery")
    public String updateBrasserie(@ModelAttribute Brasserie brasserie, HttpSession session) {
        brasserieRepository.save(brasserie);
        if (session.getAttribute("infoConnexion") != null) {
            return "redirect:/breweries";
        }
        return "redirect:/";
    }

    @PostMapping("/drop-brewery")
    public String deleteBrasserie(@ModelAttribute Brasserie brasserie, HttpSession session, Model pModel, RedirectAttributes redir) {

        ArrayList<Biere> bieres = biereRepository.getListeMarquesVersions(brasserie.getCodeBrasserie());
        pModel.addAttribute("bieres", bieres);

        boolean existance = bieres.isEmpty();
        if (session.getAttribute("infoConnexion") != null) {

            if (existance) {
                brasserieRepository.deleteById(brasserie.getCodeBrasserie());
                return "redirect:/breweries";
            } else {
                redir.addFlashAttribute("msg", "La brasserie ne peut être supprimée car des bières lui sont encore associées.");
                return "redirect:/delete-brewery/" + brasserie.getCodeBrasserie();
            }
        }
        return "redirect:/";
    }
}
