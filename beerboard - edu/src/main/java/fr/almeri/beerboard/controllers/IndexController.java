package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.Biere;
import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.repositories.BiereRepository;
import fr.almeri.beerboard.repositories.BrasserieRepository;
import fr.almeri.beerboard.repositories.MarqueRepository;
import fr.almeri.beerboard.repositories.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class IndexController {

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private MarqueRepository marqueRepository;
    @Autowired
    private BiereRepository biereRepository;

    @Autowired
    private BrasserieRepository brasserieRepository;

    @GetMapping("/index")
    public String home(Model pModel, HttpSession session) {
        if (session.getAttribute("infoConnexion") != null) {
            pModel.addAttribute("bieres", biereRepository.count());
            pModel.addAttribute("brasseries", brasserieRepository.count());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
            pModel.addAttribute("updated", dtf.format(LocalDateTime.now()));

            // Répartition des brasseries par région
            ArrayList<String> labelsPieChart = brasserieRepository.getListeRegions();
            pModel.addAttribute("labelsPieChart", labelsPieChart);
            pModel.addAttribute("datasPieChart", brasserieRepository.getRepartitionBrasserieParRegion());

            // Nombre de bières par taux d'alcool
            pModel.addAttribute("labelsAreaChart", biereRepository.getTauxAlcool());
            pModel.addAttribute("datasAreaChart", biereRepository.getNombreBieres());

            // Consommation & production de bières par pays
            ArrayList<String> labelBarChart = paysRepository.getListNomPays();
            ArrayList<Integer> datasConsommation = paysRepository.getConsommationPays();
            ArrayList<Integer> datasProduction = paysRepository.getProductionPays();
            pModel.addAttribute("labelsBarChart", labelBarChart);
            pModel.addAttribute("datasConsommation", datasConsommation);
            pModel.addAttribute("datasProduction", datasProduction);

            // Nombre de marques référencées par brasserie
            pModel.addAttribute("labelsBarChart1", marqueRepository.getListeBrasserie());
            pModel.addAttribute("datasBarChart1", marqueRepository.getNombreMarquesParBrasserie());

            // Nombre de versions par marque
            ArrayList<String> labelsBarChart2 = biereRepository.getListeMarques();
            pModel.addAttribute("labelsBarChart2", labelsBarChart2);
            pModel.addAttribute("datasBarChart2", biereRepository.getNombreVersionsParMarque());

            return "index";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Model pModel, RedirectAttributes pRedirectAttributes, HttpSession pSession) {
        pSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model pModel, RedirectAttributes pRedirectAttributes, HttpSession session) {
        if (session.getAttribute("infoConnexion") != null) {
            return "profile";
        }
        return "redirect:/";
    }
}
