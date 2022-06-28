package fr.almeri.beerboard.controllers;

//import fr.almeri.beerboard.Utils;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/")
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
        redir.addFlashAttribute("msg", "Le login et/ou le mot de passe est/sont erronés.");
        return "redirect:/";
    }

//    @PostMapping("/connect-user")
//    public String connectUtilisateur(@ModelAttribute Utilisateur utilisateur, HttpSession session, Model model) throws NoSuchAlgorithmException, NoSuchFieldException, NoSuchProviderException {
//
//        // Vérification au niveau de la base de données.
//        boolean isOk = this.checkPassword(utilisateur);
//        if (isOk)
//        {
//            // Hachage de mot de passe
//            utilisateur.setSalt(Utils.getSalt());
//            utilisateur.setMdpUtilisateur(Utils.hashMD5withSalt(utilisateur.getMdpUtilisateur(), utilisateur.getSalt()));
//            utilisateurRepository.save(utilisateur);
//            // Démarre une session
//            // Création d'une session avec
//            // utilisateur connecté
//            session.setAttribute("infoConnexion", utilisateur);
//            session.setMaxInactiveInterval(60);
//            // Si connexion Ok
//            return "redirect:/index";
//        } else {
//            model.addAttribute("isBad", true);
//            return "redirect:/";
//        }
//    }

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
                return "redirect:/";
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
                return "redirect:/";
            }
        } else {
            redir.addFlashAttribute("msg", "Mots de passe incorrects. Veuillez recommencer.");
            return "redirect:/register";
        }
    }
//    private boolean checkPassword(Utilisateur utilisateur) throws NoSuchAlgorithmException, NoSuchFieldException, NoSuchProviderException {
//        // On cherche le user en BDD à l'aide de son login
//        Utilisateur u = utilisateurRepository.compteExistant(utilisateur.getEmailUtilisateur());
//
//        // Si mot de passe ne correspond pas
//        if (u == null)
//        {
//            return false;
//        }
//
//        // Chiffrage du mot de passe saisi par l'utilisateur
//        // Pour comparaison
//        String newPass = Utils.hashMD5withSalt(utilisateur.getMdpUtilisateur(), u.getSalt());
//
//        // Retourne True si mot de passe saisi == mot de passe de la base de données
//        return newPass.equals(u.getMdpUtilisateur());
//    }
}
