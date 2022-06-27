package fr.almeri.beerboard.controllers;

import fr.almeri.beerboard.models.BiereId;
import fr.almeri.beerboard.models.Brasserie;
import fr.almeri.beerboard.models.Region;
import fr.almeri.beerboard.models.Utilisateur;
import fr.almeri.beerboard.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/login")
    public String connexion(Model pModel) {
        return "login";
    }

    @PostMapping("/connect-user")
    public String connectUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam String emailUtilisateur, RedirectAttributes redir, HttpSession session) {
        Utilisateur userTest = utilisateurRepository.compteExistant(emailUtilisateur);
        if (userTest != null) {
            if (utilisateur.getMdpUtilisateur().equals(userTest.getMdpUtilisateur())) {
                session.setAttribute("infoConnexion",userTest);
                return "redirect:/index";
            }
        }
        redir.addFlashAttribute("msg", "Informations de connexion incorrect.");
        return "redirect:/login";
    }

    @GetMapping("/motdepasse")
    public String motdepasse(Model pModel) {
        return "motdepasse";
    }

    @PostMapping("/mdp-user")
    public String mdpUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam String emailUtilisateur, RedirectAttributes redir) {
        Utilisateur userConnect = utilisateurRepository.compteExistant(emailUtilisateur);
        if (userConnect != null) {
            if (utilisateur.getEmailUtilisateur().equals(userConnect.getEmailUtilisateur())) {
                redir.addFlashAttribute("msg", "Un mail vous a été envoyé pour réinitialiser votre mot de passe.");
                return "redirect:/login";
            }
        }
        redir.addFlashAttribute("msg", "Adresse email incorrect. Vérifiez votre saisie.");
        return "redirect:/motdepasse";
    }

    @GetMapping("/register")
    public String inscription(Model pModel) {
        return "register";
    }

    @PostMapping("/valid-user")
    public String addNouvelUtilisateur(@ModelAttribute Utilisateur utilisateur, @RequestParam String emailUtilisateur, @RequestParam String inputPasswordConfirm, @RequestParam String mdpUtilisateur, Model pModel, RedirectAttributes redir) {
        if (inputPasswordConfirm.equals(mdpUtilisateur)) {
            if (utilisateurRepository.emailExistant(emailUtilisateur) != null) {
                redir.addFlashAttribute("msg", "Email déjà existant. Veuillez vous connecter en utilisant cet email.");
                return "redirect:/register";
            } else {
                utilisateurRepository.save(utilisateur);
                redir.addFlashAttribute("msg", "Le compte a bien été créé. Veuillez vous connecter.");
                return "redirect:/login";
            }
        } else {
            redir.addFlashAttribute("msg", "Mots de passe incorrects. Veuillez recommencer.");
            return "redirect:/register";
        }
    }
}
